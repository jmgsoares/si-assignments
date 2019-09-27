package main

import (
	"bufio"
	"fmt"
	"log"
	"net"
	"strings"
)

const(port = ":4321")

func main() {
	ln, err := net.Listen("tcp", port)
	if err != nil {
		log.Fatalf("could not connect: %v", err)
	}
	for {
		conn, err := ln.Accept()
		if err != nil {
			log.Fatalf("could not accept: %v", err)
		}

		message, _ := bufio.NewReader(conn).ReadString('\n')

		fmt.Printf(string(message))

		newmessage := strings.ToUpper(message)

		_, _ = conn.Write([]byte(newmessage + "\n"))
	}
}
