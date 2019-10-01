package main

import (
	ss "../protocol"
	file "../util"
	"bufio"
	"context"
	"fmt"
	"github.com/golang/protobuf/ptypes"
	"google.golang.org/grpc"
	"log"
	"os"
	"strconv"
	"time"
)

const serverAddr = "127.0.0.1:10000"

func getVehiclesFromQueryList(client ss.SearchServiceClient, owners ss.Owners) (time.Duration, time.Duration, int) {
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()

	req := &ss.SearchRequest{Payload: nil}

	req.TimeStamp = ptypes.TimestampNow()

	req.Payload = &owners

	response, err := client.GetCarsFromOwners(ctx, req)

	done, _ := ptypes.Timestamp(response.TimeStamp)
	elapsed_reply := time.Since(done)
	elapsed_request, _ := ptypes.Duration(response.Elapsed)

	if err != nil {
		log.Fatalf("%v.Somethign went wrong(_) = _, %v: ", client, err)
		return 0, 0, 0
	}

	/*fmt.Print(response.Payload.Owners)

	fmt.Println()
	fmt.Print(elapsed2)
	fmt.Printf(" ")
	fmt.Println(elapsed)
	fmt.Printf("Time total -> ")
	fmt.Println(total)
	fmt.Printf("Payload Size -> ")
	fmt.Println(float32(response.XXX_Size()) / 1024.0 / 1024.0)

	*/

	return elapsed_request, elapsed_reply, response.XXX_Size()
}

func main() {
	var opts []grpc.DialOption
	opts = append(opts, grpc.WithInsecure())

	conn, err := grpc.Dial(serverAddr, opts...)
	if err != nil {
		log.Fatalf("failed connection to server: %v", err)
		return
	}
	defer conn.Close()

	client := ss.NewSearchServiceClient(conn)

	querieList := []string{"100", "400", "850", "1700", "3400", "6785"}
	runList := []int{100, 200, 300, 400, 500}

	for _, numberRuns := range runList {

		for _, fileName := range querieList {

			println("Testing " + strconv.Itoa(numberRuns) + " runs with " + fileName + " queries")

			owners := file.LoadData("testdata/" + fileName + "_queries_4.json")

			getVehiclesFromQueryList(client, owners)

			var request, reply time.Duration
			var payloadSize int
			for i := 0; i < numberRuns; i++ {
				trequest, treply, tpayloadSize := getVehiclesFromQueryList(client, owners)
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
