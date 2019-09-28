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
	conn, err := net.Dial("tcp", port)
	if err != nil {
		log.Fatalf("failed to dial: %v", err)
	}

	_, _ = fmt.Fprintf(conn, "asd\n")
	r := message_protocol.Owner{}

	ab, err := bufio.NewReader(conn).ReadBytes('>')

	if err != nil {
		log.Fatalf("failed to read: %v", err)
	}
	err = xml.Unmarshal(ab, &r)
	os.Stdout.Write(ab)

	if err != nil {

		fmt.Printf("error  : %v", err)

		return

	}

}
