// Code generated by protoc-gen-go. DO NOT EDIT.
// source: protocol.proto

package grpc

import (
	context "context"
	fmt "fmt"
	proto "github.com/golang/protobuf/proto"
	duration "github.com/golang/protobuf/ptypes/duration"
	timestamp "github.com/golang/protobuf/ptypes/timestamp"
	grpc "google.golang.org/grpc"
	codes "google.golang.org/grpc/codes"
	status "google.golang.org/grpc/status"
	math "math"
)

// Reference imports to suppress errors if they are not otherwise used.
var _ = proto.Marshal
var _ = fmt.Errorf
var _ = math.Inf

// This is a compile-time assertion to ensure that this generated file
// is compatible with the proto package it is being compiled against.
// A compilation error at this line likely means your copy of the
// proto package needs to be updated.
const _ = proto.ProtoPackageIsVersion3 // please upgrade the proto package

type SearchRequest struct {
	Payload              *Owners              `protobuf:"bytes,1,opt,name=payload,proto3" json:"payload,omitempty"`
	TimeStamp            *timestamp.Timestamp `protobuf:"bytes,2,opt,name=timeStamp,proto3" json:"timeStamp,omitempty"`
	XXX_NoUnkeyedLiteral struct{}             `json:"-"`
	XXX_unrecognized     []byte               `json:"-"`
	XXX_sizecache        int32                `json:"-"`
}

func (m *SearchRequest) Reset()         { *m = SearchRequest{} }
func (m *SearchRequest) String() string { return proto.CompactTextString(m) }
func (*SearchRequest) ProtoMessage()    {}
func (*SearchRequest) Descriptor() ([]byte, []int) {
	return fileDescriptor_2bc2336598a3f7e0, []int{0}
}

func (m *SearchRequest) XXX_Unmarshal(b []byte) error {
	return xxx_messageInfo_SearchRequest.Unmarshal(m, b)
}
func (m *SearchRequest) XXX_Marshal(b []byte, deterministic bool) ([]byte, error) {
	return xxx_messageInfo_SearchRequest.Marshal(b, m, deterministic)
}
func (m *SearchRequest) XXX_Merge(src proto.Message) {
	xxx_messageInfo_SearchRequest.Merge(m, src)
}
func (m *SearchRequest) XXX_Size() int {
	return xxx_messageInfo_SearchRequest.Size(m)
}
func (m *SearchRequest) XXX_DiscardUnknown() {
	xxx_messageInfo_SearchRequest.DiscardUnknown(m)
}

var xxx_messageInfo_SearchRequest proto.InternalMessageInfo

func (m *SearchRequest) GetPayload() *Owners {
	if m != nil {
		return m.Payload
	}
	return nil
}

func (m *SearchRequest) GetTimeStamp() *timestamp.Timestamp {
	if m != nil {
		return m.TimeStamp
	}
	return nil
}

type SearchResponse struct {
	Payload              *Owners              `protobuf:"bytes,1,opt,name=payload,proto3" json:"payload,omitempty"`
	TimeStamp            *timestamp.Timestamp `protobuf:"bytes,2,opt,name=timeStamp,proto3" json:"timeStamp,omitempty"`
	Elapsed              *duration.Duration   `protobuf:"bytes,3,opt,name=elapsed,proto3" json:"elapsed,omitempty"`
	XXX_NoUnkeyedLiteral struct{}             `json:"-"`
	XXX_unrecognized     []byte               `json:"-"`
	XXX_sizecache        int32                `json:"-"`
}

func (m *SearchResponse) Reset()         { *m = SearchResponse{} }
func (m *SearchResponse) String() string { return proto.CompactTextString(m) }
func (*SearchResponse) ProtoMessage()    {}
func (*SearchResponse) Descriptor() ([]byte, []int) {
	return fileDescriptor_2bc2336598a3f7e0, []int{1}
}

func (m *SearchResponse) XXX_Unmarshal(b []byte) error {
	return xxx_messageInfo_SearchResponse.Unmarshal(m, b)
}
func (m *SearchResponse) XXX_Marshal(b []byte, deterministic bool) ([]byte, error) {
	return xxx_messageInfo_SearchResponse.Marshal(b, m, deterministic)
}
func (m *SearchResponse) XXX_Merge(src proto.Message) {
	xxx_messageInfo_SearchResponse.Merge(m, src)
}
func (m *SearchResponse) XXX_Size() int {
	return xxx_messageInfo_SearchResponse.Size(m)
}
func (m *SearchResponse) XXX_DiscardUnknown() {
	xxx_messageInfo_SearchResponse.DiscardUnknown(m)
}

var xxx_messageInfo_SearchResponse proto.InternalMessageInfo

func (m *SearchResponse) GetPayload() *Owners {
	if m != nil {
		return m.Payload
	}
	return nil
}

func (m *SearchResponse) GetTimeStamp() *timestamp.Timestamp {
	if m != nil {
		return m.TimeStamp
	}
	return nil
}

func (m *SearchResponse) GetElapsed() *duration.Duration {
	if m != nil {
		return m.Elapsed
	}
	return nil
}

type Car struct {
	Uid                  int32    `protobuf:"varint,1,opt,name=uid,proto3" json:"uid,omitempty"`
	Brand                string   `protobuf:"bytes,2,opt,name=brand,proto3" json:"brand,omitempty"`
	Model                string   `protobuf:"bytes,3,opt,name=model,proto3" json:"model,omitempty"`
	BodyType             string   `protobuf:"bytes,4,opt,name=bodyType,proto3" json:"bodyType,omitempty"`
	Approval             string   `protobuf:"bytes,5,opt,name=approval,proto3" json:"approval,omitempty"`
	Chassis              string   `protobuf:"bytes,6,opt,name=chassis,proto3" json:"chassis,omitempty"`
	TyreSize             string   `protobuf:"bytes,7,opt,name=tyreSize,proto3" json:"tyreSize,omitempty"`
	Weight               int32    `protobuf:"varint,8,opt,name=weight,proto3" json:"weight,omitempty"`
	Fuel                 string   `protobuf:"bytes,9,opt,name=fuel,proto3" json:"fuel,omitempty"`
	Seats                int32    `protobuf:"varint,10,opt,name=seats,proto3" json:"seats,omitempty"`
	Noise                int32    `protobuf:"varint,11,opt,name=noise,proto3" json:"noise,omitempty"`
	Co2Emissions         float32  `protobuf:"fixed32,12,opt,name=co2Emissions,proto3" json:"co2Emissions,omitempty"`
	EngineSize           int32    `protobuf:"varint,13,opt,name=engineSize,proto3" json:"engineSize,omitempty"`
	Power                int32    `protobuf:"varint,14,opt,name=power,proto3" json:"power,omitempty"`
	Color                string   `protobuf:"bytes,15,opt,name=color,proto3" json:"color,omitempty"`
	Consumption          float32  `protobuf:"fixed32,16,opt,name=consumption,proto3" json:"consumption,omitempty"`
	Plate                string   `protobuf:"bytes,17,opt,name=plate,proto3" json:"plate,omitempty"`
	Date                 string   `protobuf:"bytes,18,opt,name=date,proto3" json:"date,omitempty"`
	XXX_NoUnkeyedLiteral struct{} `json:"-"`
	XXX_unrecognized     []byte   `json:"-"`
	XXX_sizecache        int32    `json:"-"`
}

func (m *Car) Reset()         { *m = Car{} }
func (m *Car) String() string { return proto.CompactTextString(m) }
func (*Car) ProtoMessage()    {}
func (*Car) Descriptor() ([]byte, []int) {
	return fileDescriptor_2bc2336598a3f7e0, []int{2}
}

func (m *Car) XXX_Unmarshal(b []byte) error {
	return xxx_messageInfo_Car.Unmarshal(m, b)
}
func (m *Car) XXX_Marshal(b []byte, deterministic bool) ([]byte, error) {
	return xxx_messageInfo_Car.Marshal(b, m, deterministic)
}
func (m *Car) XXX_Merge(src proto.Message) {
	xxx_messageInfo_Car.Merge(m, src)
}
func (m *Car) XXX_Size() int {
	return xxx_messageInfo_Car.Size(m)
}
func (m *Car) XXX_DiscardUnknown() {
	xxx_messageInfo_Car.DiscardUnknown(m)
}

var xxx_messageInfo_Car proto.InternalMessageInfo

func (m *Car) GetUid() int32 {
	if m != nil {
		return m.Uid
	}
	return 0
}

func (m *Car) GetBrand() string {
	if m != nil {
		return m.Brand
	}
	return ""
}

func (m *Car) GetModel() string {
	if m != nil {
		return m.Model
	}
	return ""
}

func (m *Car) GetBodyType() string {
	if m != nil {
		return m.BodyType
	}
	return ""
}

func (m *Car) GetApproval() string {
	if m != nil {
		return m.Approval
	}
	return ""
}

func (m *Car) GetChassis() string {
	if m != nil {
		return m.Chassis
	}
	return ""
}

func (m *Car) GetTyreSize() string {
	if m != nil {
		return m.TyreSize
	}
	return ""
}

func (m *Car) GetWeight() int32 {
	if m != nil {
		return m.Weight
	}
	return 0
}

func (m *Car) GetFuel() string {
	if m != nil {
		return m.Fuel
	}
	return ""
}

func (m *Car) GetSeats() int32 {
	if m != nil {
		return m.Seats
	}
	return 0
}

func (m *Car) GetNoise() int32 {
	if m != nil {
		return m.Noise
	}
	return 0
}

func (m *Car) GetCo2Emissions() float32 {
	if m != nil {
		return m.Co2Emissions
	}
	return 0
}

func (m *Car) GetEngineSize() int32 {
	if m != nil {
		return m.EngineSize
	}
	return 0
}

func (m *Car) GetPower() int32 {
	if m != nil {
		return m.Power
	}
	return 0
}

func (m *Car) GetColor() string {
	if m != nil {
		return m.Color
	}
	return ""
}

func (m *Car) GetConsumption() float32 {
	if m != nil {
		return m.Consumption
	}
	return 0
}

func (m *Car) GetPlate() string {
	if m != nil {
		return m.Plate
	}
	return ""
}

func (m *Car) GetDate() string {
	if m != nil {
		return m.Date
	}
	return ""
}

type Owner struct {
	Uid                  int32    `protobuf:"varint,1,opt,name=uid,proto3" json:"uid,omitempty"`
	Name                 string   `protobuf:"bytes,2,opt,name=name,proto3" json:"name,omitempty"`
	Telephone            int32    `protobuf:"varint,3,opt,name=telephone,proto3" json:"telephone,omitempty"`
	Address              string   `protobuf:"bytes,4,opt,name=address,proto3" json:"address,omitempty"`
	LicenceNumber        int32    `protobuf:"varint,5,opt,name=licenceNumber,proto3" json:"licenceNumber,omitempty"`
	IdNumber             int32    `protobuf:"varint,6,opt,name=idNumber,proto3" json:"idNumber,omitempty"`
	TaxNumber            int32    `protobuf:"varint,7,opt,name=taxNumber,proto3" json:"taxNumber,omitempty"`
	Cars                 []*Car   `protobuf:"bytes,8,rep,name=cars,proto3" json:"cars,omitempty"`
	XXX_NoUnkeyedLiteral struct{} `json:"-"`
	XXX_unrecognized     []byte   `json:"-"`
	XXX_sizecache        int32    `json:"-"`
}

func (m *Owner) Reset()         { *m = Owner{} }
func (m *Owner) String() string { return proto.CompactTextString(m) }
func (*Owner) ProtoMessage()    {}
func (*Owner) Descriptor() ([]byte, []int) {
	return fileDescriptor_2bc2336598a3f7e0, []int{3}
}

func (m *Owner) XXX_Unmarshal(b []byte) error {
	return xxx_messageInfo_Owner.Unmarshal(m, b)
}
func (m *Owner) XXX_Marshal(b []byte, deterministic bool) ([]byte, error) {
	return xxx_messageInfo_Owner.Marshal(b, m, deterministic)
}
func (m *Owner) XXX_Merge(src proto.Message) {
	xxx_messageInfo_Owner.Merge(m, src)
}
func (m *Owner) XXX_Size() int {
	return xxx_messageInfo_Owner.Size(m)
}
func (m *Owner) XXX_DiscardUnknown() {
	xxx_messageInfo_Owner.DiscardUnknown(m)
}

var xxx_messageInfo_Owner proto.InternalMessageInfo

func (m *Owner) GetUid() int32 {
	if m != nil {
		return m.Uid
	}
	return 0
}

func (m *Owner) GetName() string {
	if m != nil {
		return m.Name
	}
	return ""
}

func (m *Owner) GetTelephone() int32 {
	if m != nil {
		return m.Telephone
	}
	return 0
}

func (m *Owner) GetAddress() string {
	if m != nil {
		return m.Address
	}
	return ""
}

func (m *Owner) GetLicenceNumber() int32 {
	if m != nil {
		return m.LicenceNumber
	}
	return 0
}

func (m *Owner) GetIdNumber() int32 {
	if m != nil {
		return m.IdNumber
	}
	return 0
}

func (m *Owner) GetTaxNumber() int32 {
	if m != nil {
		return m.TaxNumber
	}
	return 0
}

func (m *Owner) GetCars() []*Car {
	if m != nil {
		return m.Cars
	}
	return nil
}

type Owners struct {
	Owners               []*Owner `protobuf:"bytes,1,rep,name=owners,proto3" json:"owners,omitempty"`
	XXX_NoUnkeyedLiteral struct{} `json:"-"`
	XXX_unrecognized     []byte   `json:"-"`
	XXX_sizecache        int32    `json:"-"`
}

func (m *Owners) Reset()         { *m = Owners{} }
func (m *Owners) String() string { return proto.CompactTextString(m) }
func (*Owners) ProtoMessage()    {}
func (*Owners) Descriptor() ([]byte, []int) {
	return fileDescriptor_2bc2336598a3f7e0, []int{4}
}

func (m *Owners) XXX_Unmarshal(b []byte) error {
	return xxx_messageInfo_Owners.Unmarshal(m, b)
}
func (m *Owners) XXX_Marshal(b []byte, deterministic bool) ([]byte, error) {
	return xxx_messageInfo_Owners.Marshal(b, m, deterministic)
}
func (m *Owners) XXX_Merge(src proto.Message) {
	xxx_messageInfo_Owners.Merge(m, src)
}
func (m *Owners) XXX_Size() int {
	return xxx_messageInfo_Owners.Size(m)
}
func (m *Owners) XXX_DiscardUnknown() {
	xxx_messageInfo_Owners.DiscardUnknown(m)
}

var xxx_messageInfo_Owners proto.InternalMessageInfo

func (m *Owners) GetOwners() []*Owner {
	if m != nil {
		return m.Owners
	}
	return nil
}

func init() {
	proto.RegisterType((*SearchRequest)(nil), "grpc.SearchRequest")
	proto.RegisterType((*SearchResponse)(nil), "grpc.SearchResponse")
	proto.RegisterType((*Car)(nil), "grpc.Car")
	proto.RegisterType((*Owner)(nil), "grpc.Owner")
	proto.RegisterType((*Owners)(nil), "grpc.Owners")
}

func init() { proto.RegisterFile("protocol.proto", fileDescriptor_2bc2336598a3f7e0) }

var fileDescriptor_2bc2336598a3f7e0 = []byte{
	// 584 bytes of a gzipped FileDescriptorProto
	0x1f, 0x8b, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02, 0xff, 0xb4, 0x54, 0xcf, 0x6e, 0x13, 0x3f,
	0x10, 0x56, 0x9a, 0x7f, 0xcd, 0xa4, 0xed, 0xaf, 0xf5, 0xaf, 0x42, 0x26, 0x82, 0x12, 0x2d, 0x08,
	0xf5, 0xc2, 0x56, 0x4a, 0x2f, 0x9c, 0xb8, 0x14, 0x38, 0x52, 0xc9, 0xe9, 0x0b, 0x38, 0xbb, 0xd3,
	0x8d, 0xa5, 0x5d, 0xdb, 0xb5, 0x77, 0x5b, 0xc2, 0x0b, 0xf1, 0x24, 0xbc, 0x09, 0x0f, 0x82, 0x3c,
	0xde, 0x6d, 0x13, 0xe0, 0xca, 0x6d, 0xbe, 0x6f, 0xbe, 0x99, 0x6f, 0xd6, 0x93, 0x09, 0x1c, 0x59,
	0x67, 0x6a, 0x93, 0x99, 0x32, 0xa5, 0x80, 0x0d, 0x0a, 0x67, 0xb3, 0xd9, 0xab, 0xc2, 0x98, 0xa2,
	0xc4, 0x0b, 0xe2, 0x56, 0xcd, 0xed, 0x45, 0xad, 0x2a, 0xf4, 0xb5, 0xac, 0x6c, 0x94, 0xcd, 0xce,
	0x7e, 0x17, 0xe4, 0x8d, 0x93, 0xb5, 0x32, 0x3a, 0xe6, 0x93, 0x3b, 0x38, 0x5c, 0xa2, 0x74, 0xd9,
	0x5a, 0xe0, 0x5d, 0x83, 0xbe, 0x66, 0x6f, 0x61, 0x6c, 0xe5, 0xa6, 0x34, 0x32, 0xe7, 0xbd, 0x79,
	0xef, 0x7c, 0xba, 0x38, 0x48, 0x83, 0x53, 0x7a, 0xfd, 0xa0, 0xd1, 0x79, 0xd1, 0x25, 0xd9, 0x7b,
	0x98, 0x04, 0xaf, 0x65, 0xf0, 0xe2, 0x7b, 0xa4, 0x9c, 0xa5, 0xd1, 0x2c, 0xed, 0xcc, 0xd2, 0x9b,
	0x6e, 0x1a, 0xf1, 0x24, 0x4e, 0xbe, 0xf7, 0xe0, 0xa8, 0xf3, 0xf4, 0xd6, 0x68, 0x8f, 0xff, 0xde,
	0x94, 0x5d, 0xc2, 0x18, 0x4b, 0x69, 0x3d, 0xe6, 0xbc, 0x4f, 0x75, 0xcf, 0xff, 0xa8, 0xfb, 0xd8,
	0xbe, 0x8c, 0xe8, 0x94, 0xc9, 0x8f, 0x3e, 0xf4, 0xaf, 0xa4, 0x63, 0xc7, 0xd0, 0x6f, 0x54, 0x1c,
	0x6d, 0x28, 0x42, 0xc8, 0x4e, 0x61, 0xb8, 0x72, 0x52, 0xe7, 0x34, 0xc4, 0x44, 0x44, 0x10, 0xd8,
	0xca, 0xe4, 0x58, 0x92, 0xc5, 0x44, 0x44, 0xc0, 0x66, 0xb0, 0xbf, 0x32, 0xf9, 0xe6, 0x66, 0x63,
	0x91, 0x0f, 0x28, 0xf1, 0x88, 0x43, 0x4e, 0x5a, 0xeb, 0xcc, 0xbd, 0x2c, 0xf9, 0x30, 0xe6, 0x3a,
	0xcc, 0x38, 0x8c, 0xb3, 0xb5, 0xf4, 0x5e, 0x79, 0x3e, 0xa2, 0x54, 0x07, 0x43, 0x55, 0xbd, 0x71,
	0xb8, 0x54, 0xdf, 0x90, 0x8f, 0x63, 0x55, 0x87, 0xd9, 0x33, 0x18, 0x3d, 0xa0, 0x2a, 0xd6, 0x35,
	0xdf, 0xa7, 0x71, 0x5b, 0xc4, 0x18, 0x0c, 0x6e, 0x1b, 0x2c, 0xf9, 0x84, 0xf4, 0x14, 0x87, 0x79,
	0x3d, 0xca, 0xda, 0x73, 0x20, 0x69, 0x04, 0x81, 0xd5, 0x46, 0x79, 0xe4, 0xd3, 0xc8, 0x12, 0x60,
	0x09, 0x1c, 0x64, 0x66, 0xf1, 0xa9, 0x52, 0xde, 0x2b, 0xa3, 0x3d, 0x3f, 0x98, 0xf7, 0xce, 0xf7,
	0xc4, 0x0e, 0xc7, 0xce, 0x00, 0x50, 0x17, 0x4a, 0xc7, 0xc9, 0x0e, 0xa9, 0x7c, 0x8b, 0x09, 0x9d,
	0xad, 0x79, 0x40, 0xc7, 0x8f, 0x62, 0x67, 0x02, 0x81, 0xcd, 0x4c, 0x69, 0x1c, 0xff, 0x2f, 0xbe,
	0x1a, 0x01, 0x36, 0x87, 0x69, 0x66, 0xb4, 0x6f, 0x2a, 0x1b, 0x76, 0xc2, 0x8f, 0xc9, 0x6e, 0x9b,
	0xa2, 0x6e, 0xa5, 0xac, 0x91, 0x9f, 0xc4, 0x3a, 0x02, 0xe1, 0x3b, 0xf3, 0x40, 0xb2, 0xf8, 0x9d,
	0x21, 0x4e, 0x7e, 0xf6, 0x60, 0x48, 0x3f, 0xa5, 0xbf, 0x6c, 0x92, 0xc1, 0x40, 0xcb, 0x0a, 0xdb,
	0x45, 0x52, 0xcc, 0x5e, 0xc0, 0xa4, 0xc6, 0x12, 0xed, 0xda, 0x68, 0xa4, 0x5d, 0x0e, 0xc5, 0x13,
	0x11, 0xf6, 0x22, 0xf3, 0xdc, 0xa1, 0xf7, 0xed, 0x3a, 0x3b, 0xc8, 0xde, 0xc0, 0x61, 0xa9, 0x32,
	0xd4, 0x19, 0x7e, 0x69, 0xaa, 0x15, 0x3a, 0x5a, 0xe9, 0x50, 0xec, 0x92, 0x61, 0x7b, 0x2a, 0x6f,
	0x05, 0x23, 0x12, 0x3c, 0x62, 0x72, 0x96, 0x5f, 0xdb, 0xe4, 0xb8, 0x75, 0xee, 0x08, 0xf6, 0x12,
	0x06, 0x99, 0x74, 0x9e, 0xef, 0xcf, 0xfb, 0xe7, 0xd3, 0xc5, 0x24, 0xde, 0xc8, 0x95, 0x74, 0x82,
	0xe8, 0xe4, 0x1d, 0x8c, 0xe2, 0xc1, 0xb0, 0xd7, 0x30, 0x32, 0x14, 0xf1, 0x1e, 0x49, 0xa7, 0x5b,
	0xe7, 0x24, 0xda, 0xd4, 0xe2, 0xba, 0x3b, 0xfd, 0x25, 0xba, 0x7b, 0x95, 0x21, 0xfb, 0x00, 0x27,
	0x05, 0xd6, 0x57, 0xd2, 0xf9, 0xcf, 0xce, 0x54, 0x6d, 0xab, 0xff, 0x63, 0xe9, 0xce, 0x9f, 0xc4,
	0xec, 0x74, 0x97, 0x8c, 0x57, 0xbc, 0x1a, 0xd1, 0x29, 0x5d, 0xfe, 0x0a, 0x00, 0x00, 0xff, 0xff,
	0xad, 0x2d, 0x35, 0x13, 0xab, 0x04, 0x00, 0x00,
}

// Reference imports to suppress errors if they are not otherwise used.
var _ context.Context
var _ grpc.ClientConn

// This is a compile-time assertion to ensure that this generated file
// is compatible with the grpc package it is being compiled against.
const _ = grpc.SupportPackageIsVersion4

// SearchServiceClient is the client API for SearchService service.
//
// For semantics around ctx use and closing/ending streaming RPCs, please refer to https://godoc.org/google.golang.org/grpc#ClientConn.NewStream.
type SearchServiceClient interface {
	GetCarsFromOwners(ctx context.Context, in *SearchRequest, opts ...grpc.CallOption) (*SearchResponse, error)
}

type searchServiceClient struct {
	cc *grpc.ClientConn
}

func NewSearchServiceClient(cc *grpc.ClientConn) SearchServiceClient {
	return &searchServiceClient{cc}
}

func (c *searchServiceClient) GetCarsFromOwners(ctx context.Context, in *SearchRequest, opts ...grpc.CallOption) (*SearchResponse, error) {
	out := new(SearchResponse)
	err := c.cc.Invoke(ctx, "/grpc.SearchService/getCarsFromOwners", in, out, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

// SearchServiceServer is the server API for SearchService service.
type SearchServiceServer interface {
	GetCarsFromOwners(context.Context, *SearchRequest) (*SearchResponse, error)
}

// UnimplementedSearchServiceServer can be embedded to have forward compatible implementations.
type UnimplementedSearchServiceServer struct {
}

func (*UnimplementedSearchServiceServer) GetCarsFromOwners(ctx context.Context, req *SearchRequest) (*SearchResponse, error) {
	return nil, status.Errorf(codes.Unimplemented, "method GetCarsFromOwners not implemented")
}

func RegisterSearchServiceServer(s *grpc.Server, srv SearchServiceServer) {
	s.RegisterService(&_SearchService_serviceDesc, srv)
}

func _SearchService_GetCarsFromOwners_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(SearchRequest)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(SearchServiceServer).GetCarsFromOwners(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/grpc.SearchService/GetCarsFromOwners",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(SearchServiceServer).GetCarsFromOwners(ctx, req.(*SearchRequest))
	}
	return interceptor(ctx, in, info, handler)
}

var _SearchService_serviceDesc = grpc.ServiceDesc{
	ServiceName: "grpc.SearchService",
	HandlerType: (*SearchServiceServer)(nil),
	Methods: []grpc.MethodDesc{
		{
			MethodName: "getCarsFromOwners",
			Handler:    _SearchService_GetCarsFromOwners_Handler,
		},
	},
	Streams:  []grpc.StreamDesc{},
	Metadata: "protocol.proto",
}
