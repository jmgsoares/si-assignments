//go:generate protoc -I ../message_protocol --go_out=plugins=grpc:../message_protocol ../message_protocol/message_protocol.proto

package main

import (
	"context"
	"google.golang.org/grpc"
	"log"
	"net"

	ss "../message_protocol"
)

const serverAddr = "127.0.0.1:10000"

type searchServiceServer struct {
	cars []*ss.Car
}

func (s *searchServiceServer) GetCarsForOwner(context.Context, *ss.SearchRequest) (*ss.SearchResponse, error) {
	//TODO Get cars for the requested owners and return info
	panic("implement me")
}

func (s *searchServiceServer) loadVehicles() {
	//TODO Load vehicles to mem here
	panic("implement me")
}

func newServer() *searchServiceServer {
	var s searchServiceServer
	s.loadVehicles()
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
