//go:generate protoc -I ../protocol --go_out=plugins=grpc:../protocol ../protocol/protocol.proto

package main

import (
	"context"
	"encoding/json"
	"fmt"
	"google.golang.org/grpc"
	"io/ioutil"
	"log"
	"net"

	ss "../protocol"
)

const serverAddr = "127.0.0.1:10000"

type searchServiceServer struct {
	owners []*ss.Owner
}

func (s *searchServiceServer) GetCarsFromOwners(ctx context.Context, searchRequest *ss.SearchRequest) (*ss.SearchResponse, error) {

	fmt.Print("Received request for cars of owners id's: ")

	for _, owner := range searchRequest.Owners {
		fmt.Printf("%d ", owner.Uid)
	}
	fmt.Println("")

	r := ss.SearchResponse{Owners: s.owners}

	return &r, nil
}

func loadData(filePath string) []*ss.Owner {
	var data []*ss.Owner

	fileData, err := ioutil.ReadFile(filePath)

	if err != nil {
		log.Fatalf("Failed to load sample fileData: %v", err)
	}

	if err := json.Unmarshal(fileData, &data); err != nil {
		log.Fatalf("Failed to load sample fileData: %v", err)
	}

	return data
}

func newServer() *searchServiceServer {
	var s searchServiceServer
	s.owners = loadData("testdata/sampleData.json")
	return &s
}

func main() {

	lis, err := net.Listen("tcp", serverAddr)
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}
	var opts []grpc.ServerOption

	grpcServer := grpc.NewServer(opts...)

	ss.RegisterSearchServiceServer(grpcServer, newServer())

	_ = grpcServer.Serve(lis)

}
