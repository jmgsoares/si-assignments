package main

import (
	mp "../protocol"
	file "../util"
	"bytes"
	"encoding/json"
	"fmt"
	"io"
	"log"
	"net"
	"time"
)

const serverAddr = "127.0.0.1:10001"

func getVehiclesFromQueryList(owners mp.Owners) time.Duration {
	conn, err := net.Dial("tcp", serverAddr)
	if err != nil {
		log.Fatalf("failed to dial: %v", err)
	}

	defer conn.Close()

	// Start of elapsed 1
	request := mp.Request{Start: time.Now()}
	request.Owners = owners
	output, err := json.Marshal(request)

	println(string(output))

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

	o := mp.Response{}

	var buf bytes.Buffer
	_, _ = io.Copy(&buf, conn)

	err = json.Unmarshal(buf.Bytes(), &o)

	println(string(buf.Bytes()))

	// get elapsed2 time here
	elapsed2 := time.Since(o.StartS)

	if err != nil {
		fmt.Printf("error in unmarsheling: %v", err)
		return 0
	}

	total := o.Elapsed + elapsed2
	fmt.Println()
	fmt.Print(o.Elapsed)
	fmt.Printf(" ")
	fmt.Println(elapsed2)
	fmt.Printf("Time total -> ")
	fmt.Println(total.Seconds())
	fmt.Printf("Payload Size -> ")
	fmt.Println(float64(buf.Len()) / 1024.0 / 1024.0)
	fmt.Println()

	return total
}

func main() {
	owners := file.LoadData("testdata/sampleClientQuery.json")

	getVehiclesFromQueryList(owners)

	var total time.Duration

	for i := 0; i < 25; i++ {
		total += getVehiclesFromQueryList(owners)
	}

	total = total / 25
	fmt.Println()
	fmt.Printf("Mean total -> ")
	fmt.Println(total.Seconds())
}
