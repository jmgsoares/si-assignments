package main

import (
	mp "../message_protocol"
	file "../util"
	"bufio"
	"encoding/xml"
	"fmt"
	"log"
	"net"
	"time"
)

const serverAddr = "127.0.0.1:10000"

func GetCarsFromOwners(message string) mp.Request {
	result := mp.Request{}
	o := file.LoadData("testdata/sampleData.json")
	query := mp.Request{}

	_ = xml.Unmarshal([]byte(message), &query)
	//get elapsed time here
	result.Start = query.Start
	result.Elapsed = time.Since(query.Start)

	for _, owner := range query.Owners.Owners {
		result.Owners.Owners = append(result.Owners.Owners, o.Owners[owner.Uid-1])
	}

	return result
}

func main() {
	ln, err := net.Listen("tcp", serverAddr)
	if err != nil {
		log.Fatalf("could not connect: %v", err)
	}
	for {

		conn, err := ln.Accept()
		if err != nil {
			log.Fatalf("could not accept: %v", err)
		}

		message, _ := bufio.NewReader(conn).ReadString('\n')

		output := GetCarsFromOwners(message)

		result, err := xml.MarshalIndent(output, "  ", "    ")

		if err != nil {
			fmt.Printf("error in marsheling: %v\n", err)
		}

		_, _ = conn.Write(result)
		_ = conn.Close()
	}
}
