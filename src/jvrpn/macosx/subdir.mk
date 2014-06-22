################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../jni_layer.cpp 

C_UPPER_SRCS += \
../vrpn_Analog.C \
../vrpn_BaseClass.C \
../vrpn_Button.C \
../vrpn_Connection.C \
../vrpn_FileConnection.C \
../vrpn_Java.C \
../vrpn_Serial.C \
../vrpn_Shared.C 

OBJS += \
./jni_layer.o \
./vrpn_Analog.o \
./vrpn_BaseClass.o \
./vrpn_Button.o \
./vrpn_Connection.o \
./vrpn_FileConnection.o \
./vrpn_Java.o \
./vrpn_Serial.o \
./vrpn_Shared.o 

CPP_DEPS += \
./jni_layer.d 

C_UPPER_DEPS += \
./vrpn_Analog.d \
./vrpn_BaseClass.d \
./vrpn_Button.d \
./vrpn_Connection.d \
./vrpn_FileConnection.d \
./vrpn_Java.d \
./vrpn_Serial.d \
./vrpn_Shared.d 


# Each subdirectory must supply rules for building sources it contributes
%.o: ../%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -I/Library/Java/JavaVirtualMachines/jdk1.7.0_51.jdk/Contents/Home/include -I/Library/Java/JavaVirtualMachines/jdk1.7.0_51.jdk/Contents/Home/include/darwin -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

%.o: ../%.C
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -I/Library/Java/JavaVirtualMachines/jdk1.7.0_51.jdk/Contents/Home/include -I/Library/Java/JavaVirtualMachines/jdk1.7.0_51.jdk/Contents/Home/include/darwin -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


