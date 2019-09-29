package main

import (
	mp "../message_protocol"
	"bufio"
	"encoding/json"
	"encoding/xml"
	"fmt"
	"io/ioutil"
	"log"
	"net"
	"os"
)

const (
	port = ":4321"
)

func GetCarsFromOwners(message string) mp.Owners {
	result := mp.Owners{}
	o := loadData("testdata/sampleData.json")
	query := mp.Owners{}

	_ = xml.Unmarshal([]byte(message), &query)

	fmt.Println(query)

	fmt.Print("Received request for cars of owners id's: ")
	for _, owner := range query.Owners {
		fmt.Printf("%d ", owner.Uid)
	}

	for _, owner := range query.Owners {
		result.Owners = append(result.Owners, o.Owners[owner.Uid-1])
	}

	return result
}

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
	ln, err := net.Listen("tcp", port)
	if err != nil {
		log.Fatalf("could not connect: %v", err)
	}

	for {
		fmt.Println("W8ing for connection")
		conn, err := ln.Accept()
		if err != nil {
			log.Fatalf("could not accept: %v", err)
		}

		message, _ := bufio.NewReader(conn).ReadString('\n')
		fmt.Printf("Received from client -> %s\n", message)

		output := GetCarsFromOwners(message)

		result, err := xml.MarshalIndent(output, "  ", "    ")

		if err != nil {
			fmt.Printf("error: %v\n", err)
		}

		fmt.Print("Sending -> ")
		_, _ = os.Stdout.Write(result)
		_, _ = conn.Write(result)
		_ = conn.Close()
	}
}
