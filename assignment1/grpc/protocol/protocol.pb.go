// Code generated by protoc-gen-go. DO NOT EDIT.
// source: protocol.proto

package grpc

import (
	context "context"
	fmt "fmt"
	proto "github.com/golang/protobuf/proto"
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
	Payload              *Owners  `protobuf:"bytes,1,opt,name=payload,proto3" json:"payload,omitempty"`
	XXX_NoUnkeyedLiteral struct{} `json:"-"`
	XXX_unrecognized     []byte   `json:"-"`
	XXX_sizecache        int32    `json:"-"`
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

type SearchResponse struct {
	Payload              *Owners  `protobuf:"bytes,1,opt,name=payload,proto3" json:"payload,omitempty"`
	XXX_NoUnkeyedLiteral struct{} `json:"-"`
	XXX_unrecognized     []byte   `json:"-"`
	XXX_sizecache        int32    `json:"-"`
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
	// 505 bytes of a gzipped FileDescriptorProto
	0x1f, 0x8b, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02, 0xff, 0x8c, 0x93, 0xcd, 0x8e, 0xd3, 0x30,
	0x10, 0xc7, 0x95, 0x6d, 0x93, 0xb6, 0xd3, 0x0f, 0x76, 0xcd, 0x0a, 0x59, 0x2b, 0x40, 0x55, 0x41,
	0xa8, 0x17, 0x7a, 0x28, 0x07, 0x38, 0x71, 0xa9, 0xe0, 0xc8, 0x4a, 0x29, 0x2f, 0xe0, 0x3a, 0x43,
	0x6b, 0x29, 0xb1, 0x8d, 0x9d, 0x6c, 0x29, 0xef, 0xc7, 0x9b, 0xf0, 0x20, 0xc8, 0xe3, 0x64, 0x69,
	0x25, 0x0e, 0xdc, 0xe6, 0xff, 0x9b, 0x0f, 0x3b, 0xf9, 0x7b, 0x60, 0x66, 0x9d, 0xa9, 0x8d, 0x34,
	0xe5, 0x8a, 0x02, 0xd6, 0xdf, 0x3b, 0x2b, 0x17, 0xef, 0x61, 0xba, 0x45, 0xe1, 0xe4, 0x21, 0xc7,
	0xef, 0x0d, 0xfa, 0x9a, 0xbd, 0x81, 0x81, 0x15, 0xa7, 0xd2, 0x88, 0x82, 0x27, 0xf3, 0x64, 0x39,
	0x5e, 0x4f, 0x56, 0xa1, 0x70, 0x75, 0x7f, 0xd4, 0xe8, 0x7c, 0xde, 0x25, 0x17, 0x1f, 0x60, 0xd6,
	0x35, 0x7a, 0x6b, 0xb4, 0xc7, 0xff, 0xee, 0xfc, 0xd5, 0x83, 0xde, 0x46, 0x38, 0x76, 0x0d, 0xbd,
	0x46, 0xc5, 0xda, 0x34, 0x0f, 0x21, 0xbb, 0x85, 0x74, 0xe7, 0x84, 0x2e, 0xf8, 0xd5, 0x3c, 0x59,
	0x8e, 0xf2, 0x28, 0x02, 0xad, 0x4c, 0x81, 0x25, 0xef, 0x45, 0x4a, 0x82, 0xdd, 0xc1, 0x70, 0x67,
	0x8a, 0xd3, 0xd7, 0x93, 0x45, 0xde, 0xa7, 0xc4, 0xa3, 0x0e, 0x39, 0x61, 0xad, 0x33, 0x0f, 0xa2,
	0xe4, 0x69, 0xcc, 0x75, 0x9a, 0x71, 0x18, 0xc8, 0x83, 0xf0, 0x5e, 0x79, 0x9e, 0x51, 0xaa, 0x93,
	0xa1, 0xab, 0x3e, 0x39, 0xdc, 0xaa, 0x9f, 0xc8, 0x07, 0xb1, 0xab, 0xd3, 0xec, 0x19, 0x64, 0x47,
	0x54, 0xfb, 0x43, 0xcd, 0x87, 0x74, 0xdd, 0x56, 0x31, 0x06, 0xfd, 0x6f, 0x0d, 0x96, 0x7c, 0x44,
	0xf5, 0x14, 0x87, 0xfb, 0x7a, 0x14, 0xb5, 0xe7, 0x40, 0xa5, 0x51, 0x04, 0xaa, 0x8d, 0xf2, 0xc8,
	0xc7, 0x91, 0x92, 0x60, 0x0b, 0x98, 0x48, 0xb3, 0xfe, 0x54, 0x29, 0xef, 0x95, 0xd1, 0x9e, 0x4f,
	0xe6, 0xc9, 0xf2, 0x2a, 0xbf, 0x60, 0xec, 0x25, 0x00, 0xea, 0xbd, 0xd2, 0xf1, 0x66, 0x53, 0x6a,
	0x3f, 0x23, 0x61, 0xb2, 0x35, 0x47, 0x74, 0x7c, 0x16, 0x27, 0x93, 0x08, 0x54, 0x9a, 0xd2, 0x38,
	0xfe, 0x24, 0xfe, 0x35, 0x12, 0x6c, 0x0e, 0x63, 0x69, 0xb4, 0x6f, 0x2a, 0x5b, 0x2b, 0xa3, 0xf9,
	0x35, 0x1d, 0x77, 0x8e, 0x68, 0x5a, 0x29, 0x6a, 0xe4, 0x37, 0xb1, 0x8f, 0x44, 0xf8, 0xce, 0x22,
	0x40, 0x16, 0xbf, 0x33, 0xc4, 0x8b, 0xdf, 0x09, 0xa4, 0xe4, 0xed, 0x3f, 0x9c, 0x64, 0xd0, 0xd7,
	0xa2, 0xc2, 0xd6, 0x48, 0x8a, 0xd9, 0x73, 0x18, 0xd5, 0x58, 0xa2, 0x3d, 0x18, 0x8d, 0xe4, 0x65,
	0x9a, 0xff, 0x05, 0xc1, 0x17, 0x51, 0x14, 0x0e, 0xbd, 0x6f, 0xed, 0xec, 0x24, 0x7b, 0x0d, 0xd3,
	0x52, 0x49, 0xd4, 0x12, 0xbf, 0x34, 0xd5, 0x0e, 0x1d, 0x59, 0x9a, 0xe6, 0x97, 0x30, 0xb8, 0xa7,
	0x8a, 0xb6, 0x20, 0xa3, 0x82, 0x47, 0x4d, 0x27, 0x8b, 0x1f, 0x6d, 0x72, 0xd0, 0x9e, 0xdc, 0x01,
	0xf6, 0x02, 0xfa, 0x52, 0x38, 0xcf, 0x87, 0xf3, 0xde, 0x72, 0xbc, 0x1e, 0xc5, 0x47, 0xbb, 0x11,
	0x2e, 0x27, 0xbc, 0x78, 0x0b, 0x59, 0x7c, 0xc1, 0xec, 0x15, 0x64, 0x86, 0x22, 0x9e, 0x50, 0xe9,
	0xf8, 0xec, 0x7d, 0xe7, 0x6d, 0x6a, 0x7d, 0xdf, 0x2d, 0xd4, 0x16, 0xdd, 0x83, 0x92, 0xc8, 0x3e,
	0xc2, 0xcd, 0x1e, 0xeb, 0x8d, 0x70, 0xfe, 0xb3, 0x33, 0x55, 0x3b, 0xea, 0x69, 0x6c, 0xbd, 0x58,
	0xbd, 0xbb, 0xdb, 0x4b, 0x18, 0xd7, 0x6a, 0x97, 0xd1, 0xba, 0xbe, 0xfb, 0x13, 0x00, 0x00, 0xff,
	0xff, 0xb2, 0x8e, 0x40, 0x38, 0xc0, 0x03, 0x00, 0x00,
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
