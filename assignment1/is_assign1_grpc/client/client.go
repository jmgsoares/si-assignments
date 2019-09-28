package main

import (
	"context"
	"google.golang.org/grpc"
	"log"
	"time"

	ss "../message_protocol"
)

const serverAddr = "127.0.0.1:10000"

func printSearchResults(client ss.SearchServiceClient, owners []ss.Owner) {

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
	var owners []ss.Owner

	owners = append(owners,
		ss.Owner{Uid: 0},
		ss.Owner{Uid: 1},
		ss.Owner{Uid: 2},
		ss.Owner{Uid: 3},
		ss.Owner{Uid: 4},
		ss.Owner{Uid: 5})

	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()
	_, err = client.GetCarsForOwner(ctx, &ss.SearchRequest{Owners: nil})

	printSearchResults(client, owners)

	//TODO Time time to marshall all information
	//TODO Send request to server
	//TODO Parse answer & print to stdout / file
	//TODO time time to un-marshall information
}
