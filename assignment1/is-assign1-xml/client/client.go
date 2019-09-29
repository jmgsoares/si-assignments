package main

import (
	mp "../message_protocol"
	"bytes"
	"encoding/json"
	"encoding/xml"
	"fmt"
	"io"
	"io/ioutil"
	"log"
	"net"
)

const (
	port = ":4321"
)

func loadOwnersList(filePath string) []*mp.Owner {
	var owners []*mp.Owner

	data, err := ioutil.ReadFile(filePath)

	if err != nil {
		log.Fatalf("Failed to load sample data: %v", err)
	}

	if err := json.Unmarshal(data, &owners); err != nil {
		log.Fatalf("Failed to load sample data: %v", err)
	}

	return owners
}

func main() {
	conn, err := net.Dial("tcp", port)
	if err != nil {
		log.Fatalf("failed to dial: %v", err)
	}

	defer conn.Close()

	owners := loadOwnersList("testdata/sampleClientQuery.json")

	for i := range owners {
		fmt.Println(owners[i].Uid)
	}

	output, err := xml.Marshal(owners)
	_, _ = conn.Write(output)
	_, _ = fmt.Fprintf(conn, "\n")

	o := mp.Owners{}

	var buf bytes.Buffer
	_, _ = io.Copy(&buf, conn)

	err = xml.Unmarshal(buf.Bytes(), &o)

	if err != nil {
		fmt.Printf("error: %v", err)
		return
	}

	for i := range o.Owners {
		fmt.Println("User Name: " + o.Owners[i].Name)
		for j := range o.Owners[i].Cars {
			fmt.Println("	Car: " + o.Owners[i].Cars[j].Brand)
		}
	}

	/*for {
		line, err := buf.ReadBytes('\n')
		fmt.Print(string(line))
		if err == io.EOF {
			break
		}
	}*/
}
