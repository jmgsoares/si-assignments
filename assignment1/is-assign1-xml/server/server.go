package main

import (
	"../message_protocol"
	"bufio"
	"encoding/xml"
	"fmt"
	"log"
	"net"
	"os"
)

const (
	port = ":4321"
)

func main() {

	o := message_protocol.Owner{Uid: 1, Name: "Zé", Telephone: 1, Address: "a", LicenceNumber: 1, IdNumber: 1, TaxNumber: 1,
		Cars: []message_protocol.Car{
			{
				Uid:          1,
				Brand:        "a",
				Model:        "b",
				BodyType:     "c",
				Approval:     "d",
				Chassis:      "e",
				TyreSize:     "1",
				Weight:       6,
				Fuel:         "gas",
				Seats:        5,
				Noise:        2,
				Co2Emissions: 2.4,
				EngineSize:   1,
				Power:        5,
				Color:        "Black",
				Consumption:  3.2,
				Plate:        "as-23-re",
				Date:         "10",
				OwnerUid:     1,
			}}}

	ln, err := net.Listen("tcp", port)
	if err != nil {
		log.Fatalf("could not connect: %v", err)
	}

	output, err := xml.MarshalIndent(o, "  ", "    ")

	if err != nil {

		fmt.Printf("error: %v\n", err)

	}

	for {
		fmt.Println("W8ing for connection")
		conn, err := ln.Accept()
		if err != nil {
			log.Fatalf("could not accept: %v", err)
		}

		message, _ := bufio.NewReader(conn).ReadString('\n')

		fmt.Printf("Received from client -> %s\n", string(message))
		fmt.Print("Sending -> ")
		_, _ = os.Stdout.Write(output)
		_, _ = conn.Write(output)
		_ = conn.Close()
	}
}
