package main

import (
	ss "../protocol"
	"context"
	"encoding/json"
	"fmt"
	"google.golang.org/grpc"
	"io/ioutil"
	"log"
	"time"
)

const serverAddr = "127.0.0.1:10000"

func getVehiclesFromOwnersList(client ss.SearchServiceClient, owners []*ss.Owner) {
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()

	req := &ss.SearchRequest{Owners: nil}

	req.Owners = append(req.Owners, owners...)

	response, err := client.GetCarsFromOwners(ctx, req)

	if err != nil {
		log.Fatalf("%v.Somethign went wrong(_) = _, %v: ", client, err)
		return
	}

	fmt.Print(response.Owners)

}

func loadOwnersList(filePath string) []*ss.Owner {
	var owners []*ss.Owner

	data, err := ioutil.ReadFile(filePath)

	if err != nil {
		log.Fatalf("Failed to load sample data: %v", err)
	}

	if err := json.Unmarshal(data, &owners); err != nil {
		log.Fatalf("Failed to load sample data: %v", err)
	}

	return owners
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

	owners := loadOwnersList("testdata/sampleClientQuery.json")

	getVehiclesFromOwnersList(client, owners)
}
