package main

import (
	ss "../protocol"
	file "../util"
	"context"
	"fmt"
	"github.com/golang/protobuf/ptypes"
	"google.golang.org/grpc"
	"log"
	"time"
)

const serverAddr = "127.0.0.1:10000"

func getVehiclesFromQueryList(client ss.SearchServiceClient, owners ss.Owners) time.Duration {
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()

	req := &ss.SearchRequest{Payload: nil}

	req.Payload = &owners

	req.TimeStamp = ptypes.TimestampNow()

	response, err := client.GetCarsFromOwners(ctx, req)

	done, _ := ptypes.Timestamp(response.TimeStamp)
	elapsed := time.Since(done)
	elapsed2, _ := ptypes.Duration(response.Elapsed)

	total := elapsed + elapsed2

	if err != nil {
		log.Fatalf("%v.Somethign went wrong(_) = _, %v: ", client, err)
		return 0
	}

	//fmt.Print(response.Payload.Owners)

	fmt.Println()
	fmt.Print(elapsed2)
	fmt.Printf(" ")
	fmt.Println(elapsed)
	fmt.Printf("Time total -> ")
	fmt.Println(total)
	fmt.Printf("Payload Size -> ")
	fmt.Println(response.XXX_Size())

	return total
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

	var total time.Duration
	for i := 0; i < 1000; i++ {
		total += getVehiclesFromQueryList(client, owners)
	}

	total = total / 1000
	fmt.Println()
	fmt.Printf("Mean total -> ")
	fmt.Println(total)

}
