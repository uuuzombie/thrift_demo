namespace cpp sky.demo.common
namespace java com.sky.demo.thrift.common

enum UserType
{
    ADMIN = 1,
    AUDIT = 2,
    COMMON = 3
}

enum DataSourceType
{
    MYSQL = 1,
    POSTGRESQL = 2,
    ORACLE = 3
}

struct User
{
    1:string name,
    2:UserType userType,
    3:i16 age = 0,
    4:bool sex,
    5:double salary,

}