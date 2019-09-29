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
	start := time.Now()

	output, err := xml.Marshal(owners)

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

	o := mp.Owners{}

	var buf bytes.Buffer
	_, _ = io.Copy(&buf, conn)

	err = xml.Unmarshal(buf.Bytes(), &o)
	// TODO: get elapsed2 time here
	tot := time.Since(start)
	//elapsed2 := time.Since(elapsed)

	if err != nil {
		fmt.Printf("error: %v", err)
		return 0
	}

	for i := range o.Owners {
		fmt.Println("User Name: " + o.Owners[i].Name)
		for j := range o.Owners[i].Cars {
			fmt.Println("	Car: " + o.Owners[i].Cars[j].Brand)
		}
	}

	//total := elapsed + elapsed2
	//total := tot - (time in server)
	// TODO: get elapsed time 1 and 2 and the payload size
	fmt.Println()
	fmt.Println(tot)
	//fmt.Print(elapsed)
	fmt.Printf(" ")
	//fmt.Println(elapsed2)
	fmt.Printf("Time total -> ")
	//fmt.Println(total)
	fmt.Printf("Payload Size -> ")
	//fmt.Println(response.XXX_Size())

	return tot
}

func main() {
	owners := file.LoadData("testdata/sampleClientQuery.json")

	getVehiclesFromQueryList(owners)

	var total time.Duration

	for i := 0; i < 100; i++ {
		total += getVehiclesFromQueryList(owners)
	}

	total = total / 100
	fmt.Println()
	fmt.Printf("Mean total -> ")
	fmt.Println(total)
}
