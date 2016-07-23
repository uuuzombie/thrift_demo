namespace cpp sky.demo.service
namespace java com.sky.demo.thrift.service

include "common.thrift"

service DemoService
{

    list<common.User> getUserList(1:common.UserType userType)

}