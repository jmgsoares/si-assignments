package main

import (
	mp "../protocol"
	file "../util"
	"bufio"
	"bytes"
	"encoding/xml"
	"fmt"
	"io"
	"log"
	"net"
	"os"
	"strconv"
	"time"
)

const serverAddr = "127.0.0.1:10001"

func getVehiclesFromQueryList(owners mp.Owners) (time.Duration, time.Duration, int) {
	conn, err := net.Dial("tcp", serverAddr)
	if err != nil {
		log.Fatalf("failed to dial: %v", err)
	}

	defer conn.Close()

	// Start of elapsed 1
	request := mp.Request{Start: time.Now()}
	request.Owners = owners
	output, err := xml.Marshal(request)

	_, err = conn.Write(output)
	if err != nil {
		fmt.Printf("error: %v", err)
		return 0, 0, 0
	}
	_, err = fmt.Fprintf(conn, "\n")
	if err != nil {
		fmt.Printf("error: %v", err)
		return 0, 0, 0
	}

	o := mp.Response{}

	var buf bytes.Buffer
	_, _ = io.Copy(&buf, conn)

	err = xml.Unmarshal(buf.Bytes(), &o)

	// get elapsed2 time here
	elapsed2 := time.Since(o.StartS)

	if err != nil {
		fmt.Printf("error in unmarsheling: %v", err)
		return 0, 0, 0
	}

	/*for i := range o.Owners.Owners {
		fmt.Println("User Name: " + o.Owners.Owners[i].Name)
		for j := range o.Owners.Owners[i].Cars {
			fmt.Println("	Car: " + o.Owners.Owners[i].Cars[j].Brand)
		}
	}*/

	/*
		fmt.Println()
		fmt.Print(o.Elapsed)
		fmt.Printf(" ")
		fmt.Println(elapsed2)
		fmt.Printf("Time total -> ")
		fmt.Println(total)
		fmt.Printf("Payload Size -> ")
		fmt.Println(float64(buf.Len()) / 1024.0 / 1024.0)
		fmt.Println()
	*/

	return o.Elapsed, elapsed2, buf.Len()
}

func main() {

	querieList := []string{"100", "400", "850", "1700", "3400", "6785"}
	runList := []int{100, 200, 300, 400, 500}

	for _, numberRuns := range runList {

		for _, fileName := range querieList {

			println("Testing " + strconv.Itoa(numberRuns) + " runs with " + fileName + " queries")

			owners := file.LoadData("testdata/" + fileName + "_queries_4.json")
			getVehiclesFromQueryList(owners)
			var request, reply time.Duration
			var payloadSize int
			for i := 0; i < numberRuns; i++ {
				trequest, treply, tpayloadSize := getVehiclesFromQueryList(owners)
				request += trequest
				reply += treply
				payloadSize += tpayloadSize
			}

			request = request / time.Duration(numberRuns)
			reply = reply / time.Duration(numberRuns)
			payloadSize = payloadSize / numberRuns
			fmt.Printf("Mean request -> ")
			fmt.Println(request)
			fmt.Printf("Mean reply -> ")
			fmt.Println(reply)
			fmt.Printf("Mean total -> ")
			fmt.Println(request + reply)
			fmt.Printf("Mean PayloadSize -> ")
			fmt.Println(float32(payloadSize) / 1024.0 / 1024.0)
			fmt.Println()

			f, err := os.Create("results/" + fileName + "_" + strconv.Itoa(numberRuns) + ".result")
			if err != nil {
				log.Fatalf("failed connection to server: %v", err)
				return
			}

			w := bufio.NewWriter(f)

			_, err = fmt.Fprintf(w, "%f %f %f %f\n",
				float64(request)/float64(time.Millisecond),
				float64(reply)/float64(time.Millisecond),
				float64(reply)/float64(time.Millisecond) + float64(request)/float64(time.Millisecond),
				float32(payloadSize)/1024.0/1024.0)
			if err != nil {
				log.Fatalf("failed connection to server: %v", err)
				return
			}
			err = w.Flush()
			if err != nil {
				log.Fatalf("failed connection to server: %v", err)
				return
			}
			f.Close()
		}
	}
}
