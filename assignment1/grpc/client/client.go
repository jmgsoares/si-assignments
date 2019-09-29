package main

import (
	ss "../protocol"
	file "../util"
	"context"
	"fmt"
	"google.golang.org/grpc"
	"log"
	"time"
)

const serverAddr = "127.0.0.1:10000"

func getVehiclesFromQueryList(client ss.SearchServiceClient, owners ss.Owners) {
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()

	req := &ss.SearchRequest{Payload: nil}

	req.Payload = &owners

	response, err := client.GetCarsFromOwners(ctx, req)

	if err != nil {
		log.Fatalf("%v.Somethign went wrong(_) = _, %v: ", client, err)
		return
	}

	fmt.Print(response.Payload.Owners)
}

func main() {
	var opts []grpc.DialOption
	opts = append(opts, grpc.WithInsecure())

	conn, err := grpc.Dial(serverAddr, opts...)
	if err != nil {
		log.Fatalf("failed connection to server: %v", err)
		return
	}
	defer conn.Close()

	client := ss.NewSearchServiceClient(conn)

	owners := file.LoadData("testdata/sampleClientQuery.json")

	getVehiclesFromQueryList(client, owners)
}
