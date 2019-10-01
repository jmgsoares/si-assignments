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

func getVehiclesFromQueryList(client ss.SearchServiceClient, owners ss.Owners) (time.Duration, int) {
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()

	req := &ss.SearchRequest{Payload: nil}

	req.Payload = &owners

	req.TimeStamp = ptypes.TimestampNow()

	response, err := client.GetCarsFromOwners(ctx, req)

	done, _ := ptypes.Timestamp(response.TimeStamp)
	elapsed := time.Since(done)
	elapsed2, _ := ptypes.Duration(response.Elapsed)

	total := elapsed + elapsed2

	if err != nil {
		log.Fatalf("%v.Somethign went wrong(_) = _, %v: ", client, err)
		return 0, 0
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

	return total, response.XXX_Size()
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

			var total time.Duration
			var payloadSize int
			for i := 0; i < numberRuns; i++ {
				ttotal, tpayloadSize := getVehiclesFromQueryList(client, owners)
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
