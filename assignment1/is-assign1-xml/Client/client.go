package main

import (
	"bufio"
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

	fmt.Printf(status)
}
