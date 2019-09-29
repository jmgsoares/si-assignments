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

func loadData(filePath string) mp.Owners {
	var data mp.Owners

	fileData, err := ioutil.ReadFile(filePath)

	if err != nil {
		log.Fatalf("Failed to load sample fileData: %v", err)
	}

	if err := json.Unmarshal(fileData, &data); err != nil {
		log.Fatalf("Failed to load sample fileData: %v", err)
	}

	return data
}

func main() {
	conn, err := net.Dial("tcp", port)
	if err != nil {
		log.Fatalf("failed to dial: %v", err)
	}

	defer conn.Close()

	owners := loadData("testdata/sampleClientQuery.json")

	for i := range owners.Owners {
		fmt.Println(owners.Owners[i].Uid)
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
