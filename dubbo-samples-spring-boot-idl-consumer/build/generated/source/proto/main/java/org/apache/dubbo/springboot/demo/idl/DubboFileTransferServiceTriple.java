/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.apache.dubbo.springboot.demo.idl;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.PathResolver;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.ServerService;
import org.apache.dubbo.rpc.TriRpcStatus;
import org.apache.dubbo.rpc.model.MethodDescriptor;
import org.apache.dubbo.rpc.model.ServiceDescriptor;
import org.apache.dubbo.rpc.model.StubMethodDescriptor;
import org.apache.dubbo.rpc.model.StubServiceDescriptor;
import org.apache.dubbo.rpc.stub.BiStreamMethodHandler;
import org.apache.dubbo.rpc.stub.ServerStreamMethodHandler;
import org.apache.dubbo.rpc.stub.StubInvocationUtil;
import org.apache.dubbo.rpc.stub.StubInvoker;
import org.apache.dubbo.rpc.stub.StubMethodHandler;
import org.apache.dubbo.rpc.stub.StubSuppliers;
import org.apache.dubbo.rpc.stub.UnaryStubMethodHandler;

import com.google.protobuf.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.concurrent.CompletableFuture;

public final class DubboFileTransferServiceTriple {

    public static final String SERVICE_NAME = FileTransferService.SERVICE_NAME;

    private static final StubServiceDescriptor serviceDescriptor = new StubServiceDescriptor(SERVICE_NAME,FileTransferService.class);

    static {
        org.apache.dubbo.rpc.protocol.tri.service.SchemaDescriptorRegistry.addSchemaDescriptor(SERVICE_NAME,Transfer.getDescriptor());
        StubSuppliers.addSupplier(SERVICE_NAME, DubboFileTransferServiceTriple::newStub);
        StubSuppliers.addSupplier(FileTransferService.JAVA_SERVICE_NAME,  DubboFileTransferServiceTriple::newStub);
        StubSuppliers.addDescriptor(SERVICE_NAME, serviceDescriptor);
        StubSuppliers.addDescriptor(FileTransferService.JAVA_SERVICE_NAME, serviceDescriptor);
    }

    @SuppressWarnings("all")
    public static FileTransferService newStub(Invoker<?> invoker) {
        return new FileTransferServiceStub((Invoker<FileTransferService>)invoker);
    }

    private static final StubMethodDescriptor transferMethod = new StubMethodDescriptor("transfer",
    org.apache.dubbo.springboot.demo.idl.FileSlice.class, org.apache.dubbo.springboot.demo.idl.FileSlice.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), org.apache.dubbo.springboot.demo.idl.FileSlice::parseFrom,
    org.apache.dubbo.springboot.demo.idl.FileSlice::parseFrom);

    private static final StubMethodDescriptor transferAsyncMethod = new StubMethodDescriptor("transfer",
    org.apache.dubbo.springboot.demo.idl.FileSlice.class, java.util.concurrent.CompletableFuture.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), org.apache.dubbo.springboot.demo.idl.FileSlice::parseFrom,
    org.apache.dubbo.springboot.demo.idl.FileSlice::parseFrom);

    private static final StubMethodDescriptor transferProxyAsyncMethod = new StubMethodDescriptor("transferAsync",
    org.apache.dubbo.springboot.demo.idl.FileSlice.class, org.apache.dubbo.springboot.demo.idl.FileSlice.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), org.apache.dubbo.springboot.demo.idl.FileSlice::parseFrom,
    org.apache.dubbo.springboot.demo.idl.FileSlice::parseFrom);




    static{
        serviceDescriptor.addMethod(transferMethod);
        serviceDescriptor.addMethod(transferProxyAsyncMethod);
    }

    public static class FileTransferServiceStub implements FileTransferService{
        private final Invoker<FileTransferService> invoker;

        public FileTransferServiceStub(Invoker<FileTransferService> invoker) {
            this.invoker = invoker;
        }

        @Override
        public org.apache.dubbo.springboot.demo.idl.FileSlice transfer(org.apache.dubbo.springboot.demo.idl.FileSlice request){
            return StubInvocationUtil.unaryCall(invoker, transferMethod, request);
        }

        public CompletableFuture<org.apache.dubbo.springboot.demo.idl.FileSlice> transferAsync(org.apache.dubbo.springboot.demo.idl.FileSlice request){
            return StubInvocationUtil.unaryCall(invoker, transferAsyncMethod, request);
        }

        public void transfer(org.apache.dubbo.springboot.demo.idl.FileSlice request, StreamObserver<org.apache.dubbo.springboot.demo.idl.FileSlice> responseObserver){
            StubInvocationUtil.unaryCall(invoker, transferMethod , request, responseObserver);
        }



    }

    public static abstract class FileTransferServiceImplBase implements FileTransferService, ServerService<FileTransferService> {

        private <T, R> BiConsumer<T, StreamObserver<R>> syncToAsync(java.util.function.Function<T, R> syncFun) {
            return new BiConsumer<T, StreamObserver<R>>() {
                @Override
                public void accept(T t, StreamObserver<R> observer) {
                    try {
                        R ret = syncFun.apply(t);
                        observer.onNext(ret);
                        observer.onCompleted();
                    } catch (Throwable e) {
                        observer.onError(e);
                    }
                }
            };
        }

        @Override
        public CompletableFuture<org.apache.dubbo.springboot.demo.idl.FileSlice> transferAsync(org.apache.dubbo.springboot.demo.idl.FileSlice request){
                return CompletableFuture.completedFuture(transfer(request));
        }

        /**
        * This server stream type unary method is <b>only</b> used for generated stub to support async unary method.
        * It will not be called if you are NOT using Dubbo3 generated triple stub and <b>DO NOT</b> implement this method.
        */
        public void transfer(org.apache.dubbo.springboot.demo.idl.FileSlice request, StreamObserver<org.apache.dubbo.springboot.demo.idl.FileSlice> responseObserver){
            transferAsync(request).whenComplete((r, t) -> {
                if (t != null) {
                    responseObserver.onError(t);
                } else {
                    responseObserver.onNext(r);
                    responseObserver.onCompleted();
                }
            });
        }

        @Override
        public final Invoker<FileTransferService> getInvoker(URL url) {
            PathResolver pathResolver = url.getOrDefaultFrameworkModel()
            .getExtensionLoader(PathResolver.class)
            .getDefaultExtension();
            Map<String,StubMethodHandler<?, ?>> handlers = new HashMap<>();

            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/transfer");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/transferAsync");
            // for compatibility
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/transfer");
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/transferAsync");


            BiConsumer<org.apache.dubbo.springboot.demo.idl.FileSlice, StreamObserver<org.apache.dubbo.springboot.demo.idl.FileSlice>> transferFunc = this::transfer;
            handlers.put(transferMethod.getMethodName(), new UnaryStubMethodHandler<>(transferFunc));
            BiConsumer<org.apache.dubbo.springboot.demo.idl.FileSlice, StreamObserver<org.apache.dubbo.springboot.demo.idl.FileSlice>> transferAsyncFunc = syncToAsync(this::transfer);
            handlers.put(transferProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(transferAsyncFunc));




            return new StubInvoker<>(this, url, FileTransferService.class, handlers);
        }


        @Override
        public org.apache.dubbo.springboot.demo.idl.FileSlice transfer(org.apache.dubbo.springboot.demo.idl.FileSlice request){
            throw unimplementedMethodException(transferMethod);
        }





        @Override
        public final ServiceDescriptor getServiceDescriptor() {
            return serviceDescriptor;
        }
        private RpcException unimplementedMethodException(StubMethodDescriptor methodDescriptor) {
            return TriRpcStatus.UNIMPLEMENTED.withDescription(String.format("Method %s is unimplemented",
                "/" + serviceDescriptor.getInterfaceName() + "/" + methodDescriptor.getMethodName())).asException();
        }
    }

}
