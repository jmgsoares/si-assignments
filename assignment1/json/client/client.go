package main

import (
	mp "../protocol"
	file "../util"
	"bufio"
	"bytes"
	"encoding/json"
	"fmt"
	"io"
	"log"
	"net"
	"os"
	"strconv"
	"time"
)

const serverAddr = "127.0.0.1:10001"

func getVehiclesFromQueryList(owners mp.Owners) (time.Duration, int) {
	conn, err := net.Dial("tcp", serverAddr)
	if err != nil {
		log.Fatalf("failed to dial: %v", err)
	}

	defer conn.Close()

	// Start of elapsed 1
	request := mp.Request{Start: time.Now()}
	request.Owners = owners
	output, err := json.Marshal(request)

	//println(string(output))

	_, err = conn.Write(output)

	if err != nil {
		fmt.Printf("error: %v", err)
		return 0, 0
	}
	_, err = fmt.Fprintf(conn, "\n")
	if err != nil {
		fmt.Printf("error: %v", err)
		return 0, 0
	}

	o := mp.Response{}

	var buf bytes.Buffer
	_, _ = io.Copy(&buf, conn)

	err = json.Unmarshal(buf.Bytes(), &o)

	//println(string(buf.Bytes()))

	// get elapsed2 time here
	elapsed2 := time.Since(o.StartS)

	if err != nil {
		fmt.Printf("error in unmarsheling: %v", err)
		return 0, 0
	}

	total := o.Elapsed + elapsed2

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
	return total, buf.Len()
}

func main() {
	querieList := []string{"100", "400", "850", "1700", "3400", "6785"}
	runList := []int{100, 200, 300, 400, 500}

	for _, numberRuns := range runList {

		for _, fileName := range querieList {

			println("Testing " + strconv.Itoa(numberRuns) + " runs with " + fileName + " queries")

			owners := file.LoadData("testdata/" + fileName + "_queries_4.json")
			getVehiclesFromQueryList(owners)
			var total time.Duration
			var payloadSize int
			for i := 0; i < numberRuns; i++ {
				ttotal, tpayloadSize := getVehiclesFromQueryList(owners)
				total += ttotal
				payloadSize += tpayloadSize
			}

			total = total / time.Duration(numberRuns)
			payloadSize = payloadSize / numberRuns
			fmt.Printf("Mean total -> ")
			fmt.Println(total)
			fmt.Printf("Mean PayloadSize -> ")
			fmt.Println(float32(payloadSize) / 1024.0 / 1024.0)
			fmt.Println()

			f, err := os.Create("results/" + fileName + "_" + strconv.Itoa(numberRuns) + ".result")
			if err != nil {
				log.Fatalf("failed connection to server: %v", err)
				return
			}

			w := bufio.NewWriter(f)

			_, err = fmt.Fprintf(w, "%f %f\n", float64(total)/float64(time.Millisecond), float32(payloadSize)/1024.0/1024.0)
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
