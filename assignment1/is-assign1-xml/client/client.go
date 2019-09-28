package main

import (
	"bytes"
	"fmt"
	"io"
	"log"
	"net"
)

const (
	port = ":4321"
)

func main() {
	conn, err := net.Dial("tcp", port)
	if err != nil {
		log.Fatalf("failed to dial: %v", err)
	}

	_, _ = fmt.Fprintf(conn, "Hello, this is client\n")

	var buf bytes.Buffer
	_, _ = io.Copy(&buf, conn)
	fmt.Println("total size:", buf.Len())

	for {
		line, err := buf.ReadBytes('\n')
		fmt.Print(string(line))
		if err == io.EOF {
			break
		}
	}
}
