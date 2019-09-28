package main

import (
	ss "../message_protocol"
	"context"
	"fmt"
	"google.golang.org/grpc"
	"log"
	"time"
)

const serverAddr = "127.0.0.1:10000"

func printSearchResults(client ss.SearchServiceClient, owners []ss.Owner) {
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()
	req := &ss.SearchRequest{Owners: nil}
	req.Owners = append(req.Owners, &ss.Owner{
		Uid: 0,
	}, &ss.Owner{
		Uid: 1,
	}, &ss.Owner{
		Uid: 2,
	}, &ss.Owner{
		Uid: 3,
	})
	response, err := client.GetCarsForOwner(ctx, req)
	if err != nil {
		log.Fatalf("%v.Somethign went wrong(_) = _, %v: ", client, err)
	}
	for _, owner := range response.Owners {
		fmt.Println(owner)
	}

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

	printSearchResults(client, nil)

	//TODO Time time to marshall all information
	//TODO Send request to server
	//TODO Parse answer & print to stdout / file
	//TODO time time to un-marshall information
}
