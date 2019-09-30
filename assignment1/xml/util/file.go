package util

import (
	mp "../protocol"
	"encoding/json"
	"io/ioutil"
	"log"
)

func LoadData(filePath string) mp.Owners {
	var data mp.Owners

	fileData, err := ioutil.ReadFile(filePath)

	if err != nil {
		log.Fatalf("Failed to load sample fileData: %v", err)
	}

	if err := json.Unmarshal(fileData, &data); err != nil {
		log.Fatalf("Failed to load sample fileData: %v", err)
	}

	return data
}
