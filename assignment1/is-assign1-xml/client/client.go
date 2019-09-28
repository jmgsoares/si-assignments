package main

import (
	"../message_protocol"
	"bufio"
	"encoding/xml"
	"fmt"
	"log"
	"net"
)

const(port = ":4321")

func main() {
	conn, err := net.Dial("tcp", port)
	if err != nil {
		log.Fatalf("failed to dial: %v", err)
	}

	_, _ = fmt.Fprintf(conn, "asd\n")
	status, err := bufio.NewReader(conn).ReadString('\n')
	if err!= nil {
		log.Fatalf("failed to read: %v", err)
	}

	r := message_protocol.Owner{}
	err = xml.Unmarshal([]byte(status), &r)

	fmt.Printf(status)

	if err != nil {

		fmt.Printf("error: %v", err)

		return

	}

	fmt.Printf(string(r.Uid))
}
