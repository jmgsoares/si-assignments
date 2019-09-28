package main

import (
	"google.golang.org/grpc"
	"log"

	ss "../message_protocol"
)

const serverAddr = "127.0.0.1:10000"

func printSearchResults(client ss.SearchServiceClient) {

}

func main() {
	var opts []grpc.DialOption
	opts = append(opts, grpc.WithInsecure())

	conn, err := grpc.Dial(serverAddr, opts...)

	if err != nil {
		log.Fatalf("fail to dial: %v", err)
	}

	defer conn.Close()
	client := ss.NewSearchServiceClient(conn)

	//TODO Get owners whom to search for cars -> build objects to memory
	//TODO Time time to marshall all information
	//TODO Send request to server
	//TODO Parse answer & print to stdout / file
	//TODO time time to un-marshall information
}
