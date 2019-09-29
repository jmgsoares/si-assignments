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

func GetCarsFromOwners(message string) mp.Owners {
	result := mp.Owners{}
	o := file.LoadData("testdata/sampleData.json")
	query := mp.Owners{}

	_ = xml.Unmarshal([]byte(message), &query)
	//get elapsed time here
	mp.StartS = time.Now()

	for _, owner := range query.Owners {
		result.Owners = append(result.Owners, o.Owners[owner.Uid-1])
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

		//start elapsed2 time here
		mp.ElapsedS1 = time.Since(mp.StartS)

		result, err := xml.MarshalIndent(output, "  ", "    ")

		if err != nil {
			fmt.Printf("error: %v\n", err)
		}

		_, _ = conn.Write(result)
		_ = conn.Close()
	}
}
