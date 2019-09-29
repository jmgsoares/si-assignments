//go:generate protoc -I ../protocol --go_out=plugins=grpc:../protocol ../protocol/protocol.proto

package main

import (
	ss "../protocol"
	file "../util"
	"context"
	"fmt"
	"github.com/golang/protobuf/ptypes"
	"google.golang.org/grpc"
	"log"
	"net"
	"time"
)

const serverAddr = "127.0.0.1:10000"

type searchServiceServer struct {
	data ss.Owners
}

func (s *searchServiceServer) GetCarsFromOwners(ctx context.Context, searchRequest *ss.SearchRequest) (*ss.SearchResponse, error) {

	start, _ := ptypes.Timestamp(searchRequest.TimeStamp)
	elapsed := time.Since(start)

	fmt.Print("Received request for cars of owners id's: ")
	for _, owner := range searchRequest.Payload.Owners {
		fmt.Printf("%d ", owner.Uid)
	}
	fmt.Println("")

	r := ss.SearchResponse{Payload: new(ss.Owners)}
	r.Elapsed = ptypes.DurationProto(elapsed)

	for _, owner := range searchRequest.Payload.Owners {
		r.Payload.Owners = append(r.Payload.Owners, s.data.Owners[owner.Uid-1])
	}

	r.TimeStamp = ptypes.TimestampNow()
	return &r, nil
}

func newServer() *searchServiceServer {
	var s searchServiceServer
	s.data = file.LoadData("testdata/sampleData.json")
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
