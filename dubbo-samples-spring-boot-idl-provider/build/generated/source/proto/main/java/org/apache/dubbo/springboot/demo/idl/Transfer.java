// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: transfer.proto

package org.apache.dubbo.springboot.demo.idl;

public final class Transfer {
  private Transfer() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_apache_dubbo_springboot_demo_idl_FileSlice_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_apache_dubbo_springboot_demo_idl_FileSlice_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\016transfer.proto\022$org.apache.dubbo.sprin" +
      "gboot.demo.idl\"\034\n\tFileSlice\022\017\n\007content\030\001" +
      " \003(\0142\203\001\n\023FileTransferService\022l\n\010transfer" +
      "\022/.org.apache.dubbo.springboot.demo.idl." +
      "FileSlice\032/.org.apache.dubbo.springboot." +
      "demo.idl.FileSliceB\002P\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_org_apache_dubbo_springboot_demo_idl_FileSlice_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_org_apache_dubbo_springboot_demo_idl_FileSlice_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_apache_dubbo_springboot_demo_idl_FileSlice_descriptor,
        new java.lang.String[] { "Content", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
