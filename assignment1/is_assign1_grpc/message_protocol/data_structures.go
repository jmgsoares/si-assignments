package message_protocol

type Car struct {
	uid          int
	brand        string
	model        string
	bodyType     string
	approval     string
	chassis      string
	tyreSize     string
	weight       int
	fuel         string
	seats        int
	noise        int
	co2Emissions float32
	engineSize   int
	power        int
	color        string
	consumption  float32
	plate        string
	date         string
	ownerUid     int
}

type Owner struct {
	uid           int
	name          string
	telephone     int
	address       string
	licenceNumber int
	idNumber      int
	taxNumber     int
	cars          []Car
}
