//go:generate protoc -I ../message_protocol --go_out=plugins=grpc:../message_protocol ../message_protocol/message_protocol.proto

package main

import (
	"context"
	"fmt"
	"google.golang.org/grpc"
	"log"
	"net"

	ss "../message_protocol"
)

const serverAddr = "127.0.0.1:10000"

type searchServiceServer struct {
	cars []*ss.Car
}

func (s *searchServiceServer) GetCarsForOwner(ctx context.Context, searchRequest *ss.SearchRequest) (*ss.SearchResponse, error) {

	fmt.Print("Received request for cars of: ")

	for _, owner := range searchRequest.Owners {
		fmt.Printf("%d ", owner.Uid)
	}

	r := ss.SearchResponse{Owners: nil}
	r.Owners = append(r.Owners, &ss.Owner{
		Uid:           1,
		Name:          "Ivanovsky aka 4 legged",
		Telephone:     123123123,
		Address:       "radioactive street, chernobyl",
		LicenceNumber: 124352,
		IdNumber:      025131,
		TaxNumber:     3212120,
		Cars:          s.cars,
	},
		&ss.Owner{
			Uid:           1,
			Name:          "aka 4 legged",
			Telephone:     123123123,
			Address:       "radioactive street, chernobyl",
			LicenceNumber: 124352,
			IdNumber:      025131,
			TaxNumber:     3212120,
			Cars:          s.cars,
		},
		&ss.Owner{
			Uid:           1,
			Name:          "4 legged",
			Telephone:     123123123,
			Address:       "radioactive street, chernobyl",
			LicenceNumber: 124352,
			IdNumber:      025131,
			TaxNumber:     3212120,
			Cars:          s.cars,
		},
		&ss.Owner{
			Uid:           1,
			Name:          "legged",
			Telephone:     123123123,
			Address:       "radioactive street, chernobyl",
			LicenceNumber: 124352,
			IdNumber:      025131,
			TaxNumber:     3212120,
			Cars:          s.cars,
		})
	return &r, nil
}

func (s *searchServiceServer) loadVehicles() {
	s.cars = append(s.cars, &ss.Car{
		Uid:          12,
		Brand:        "Toyota",
		Model:        "Corolla",
		BodyType:     "Van",
		Approval:     "3d24afs563e2xx021",
		Chassis:      "fas353132wesx00",
		TyreSize:     "205/55R16",
		Weight:       850,
		Fuel:         "Diesel",
		Seats:        2,
		Noise:        50,
		Co2Emissions: 20,
		EngineSize:   2000,
		Power:        50,
		Color:        "Black",
		Consumption:  5.5,
		Plate:        "AB12CD",
		Date:         "20/20/2020",
		OwnerUid:     1,
	},
		&ss.Car{
			Uid:          12,
			Brand:        "Toyota1",
			Model:        "Corolla",
			BodyType:     "Van",
			Approval:     "3d24afs563e2xx021",
			Chassis:      "fas353132wesx00",
			TyreSize:     "205/55R16",
			Weight:       850,
			Fuel:         "Diesel",
			Seats:        2,
			Noise:        50,
			Co2Emissions: 20,
			EngineSize:   2000,
			Power:        50,
			Color:        "Black",
			Consumption:  5.5,
			Plate:        "AB12CD",
			Date:         "20/20/2020",
			OwnerUid:     1,
		},
		&ss.Car{
			Uid:          12,
			Brand:        "Toyota2",
			Model:        "Corolla",
			BodyType:     "Van",
			Approval:     "3d24afs563e2xx021",
			Chassis:      "fas353132wesx00",
			TyreSize:     "205/55R16",
			Weight:       850,
			Fuel:         "Diesel",
			Seats:        2,
			Noise:        50,
			Co2Emissions: 20,
			EngineSize:   2000,
			Power:        50,
			Color:        "Black",
			Consumption:  5.5,
			Plate:        "AB12CD",
			Date:         "20/20/2020",
			OwnerUid:     1,
		})
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
