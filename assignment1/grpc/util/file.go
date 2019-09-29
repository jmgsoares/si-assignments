package util

import (
	"encoding/json"
	"io/ioutil"
	"log"

	ss "../protocol"
)

func LoadData(filePath string) ss.Owners {
	var data ss.Owners

	fileData, err := ioutil.ReadFile(filePath)

	if err != nil {
		log.Fatalf("Failed to load sample fileData: %v", err)
	}

	if err := json.Unmarshal(fileData, &data); err != nil {
		log.Fatalf("Failed to load sample fileData: %v", err)
	}

	return data
}
