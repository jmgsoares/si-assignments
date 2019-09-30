package main

import (
	mp "../message_protocol"
	file "../util"
	"bytes"
	"encoding/xml"
	"fmt"
	"io"
	"log"
	"net"
	"time"
)

const serverAddr = "127.0.0.1:10000"

func getVehiclesFromQueryList(owners mp.Owners) time.Duration {
	conn, err := net.Dial("tcp", serverAddr)
	if err != nil {
		log.Fatalf("failed to dial: %v", err)
	}

	defer conn.Close()

	// Start of elapsed 1
	request := mp.Request{Start: time.Now()}
	request.Owners = owners
	output, err := xml.Marshal(request)

	_, err = conn.Write(output)
	if err != nil {
		fmt.Printf("error: %v", err)
		return 0
	}
	_, err = fmt.Fprintf(conn, "\n")
	if err != nil {
		fmt.Printf("error: %v", err)
		return 0
	}

	o := mp.Request{}

	var buf bytes.Buffer
	_, _ = io.Copy(&buf, conn)

	err = xml.Unmarshal(buf.Bytes(), &o)

	// get elapsed2 time here
	o.Elapsed2 = time.Since(request.Start)

	if err != nil {
		fmt.Printf("error in unmarsheling: %v", err)
		return 0
	}

	/*for i := range o.Owners.Owners {
		fmt.Println("User Name: " + o.Owners.Owners[i].Name)
		for j := range o.Owners.Owners[i].Cars {
			fmt.Println("	Car: " + o.Owners.Owners[i].Cars[j].Brand)
		}
	}*/

	total := o.Elapsed + o.Elapsed2
	fmt.Println()
	fmt.Print(o.Start)
	fmt.Println()
	fmt.Print(o.Elapsed)
	fmt.Printf(" ")
	fmt.Println(o.Elapsed2)
	fmt.Println()
	fmt.Printf("Time total -> ")
	fmt.Println(total)
	fmt.Printf("Payload Size -> ")
	fmt.Println(buf.Len())
	fmt.Println()

	return total
}

func main() {
	owners := file.LoadData("testdata/sampleClientQuery.json")

	getVehiclesFromQueryList(owners)

	var total time.Duration

	for i := 0; i < 1; i++ {
		total += getVehiclesFromQueryList(owners)
	}

	total = total / 1
	fmt.Println()
	fmt.Printf("Mean total -> ")
	fmt.Println(total)
}
