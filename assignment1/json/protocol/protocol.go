package protocol

import (
	"time"
)

type Request struct {
	Owners Owners
	Start  time.Time
}

type Response struct {
	Owners  Owners
	Elapsed time.Duration
	StartS  time.Time
}

type Owners struct {
	Owners []Owner
}

type Owner struct {
	Uid           int
	Name          string
	Telephone     int
	Address       string
	LicenceNumber int
	IdNumber      int
	TaxNumber     int
	Cars          []Car
}

type Car struct {
	Uid          int
	Brand        string
	Model        string
	BodyType     string
	Approval     string
	Chassis      string
	TyreSize     string
	Weight       int
	Fuel         string
	Seats        int
	Noise        int
	Co2Emissions float32
	EngineSize   int
	Power        int
	Color        string
	Consumption  float32
	Plate        string
	Date         string
	OwnerUid     int
}
