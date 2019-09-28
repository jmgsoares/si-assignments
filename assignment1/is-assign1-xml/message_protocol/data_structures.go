package message_protocol

import (
	"encoding/xml"
)

type Car struct {
	XMLName   		xml.Name 	`xml:"car"`
	Uid				int      	`xml:"uid"`
	Brand			string		`xml:"brand"`
	Model			string		`xml:"model"`
	BodyType		string		`xml:"bodyType"`
	Approval		string		`xml:"approval"`
	Chassis			string		`xml:"chassis"`
	TyreSize		string		`xml:"tyreSize"`
	Weight          int			`xml:"weight"`
	Fuel			string		`xml:"fuel"`
	Seats			int			`xml:"seats"`
	Noise           int			`xml:"noise"`
	Co2Emissions    float32		`xml:"co2Emissions"`
	EngineSize      int			`xml:"engineSize"`
	Power          	int			`xml:"power"`
	Color			string		`xml:"color"`
	Consumption  	float32		`xml:"consumption"`
	Plate			string		`xml:"plate"`
	Date			string		`xml:"date"`
	OwnerUid		int			`xml:"ownerUid"`
}

type Owner struct {
	XMLName       	xml.Name 	`xml:"owner"`
	Uid           	int      	`xml:"uid"`
	Name          	string   	`xml:"name"`
	Telephone     	int      	`xml:"telephone"`
	Address       	string   	`xml:"address"`
	LicenceNumber 	int      	`xml:"licenceNumber"`
	IdNumber      	int      	`xml:"idNumber"`
	TaxNumber     	int      	`xml:"taxNumber"`
	Cars          	[]Car
}
