package com.mytest.retrofit;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface GetRequest_Interface {

    @GET("openapi.do?keyfrom=Yanzhikai&key=2032414398&type=data&doctype=json&version=1.1&q=car")
    Call<ResponseBody> getCall();
    //@GET注解的作用,采用Get方法发送网络请求
    //getCall() = 接收网络请求数据的方法
    //其中返回类型为Call<*> *是接收数据的类
    //如果想直接获得Responsebody中的内容,可以定义网络请求返回值为Call<ResponseBody>

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://fanyi.youdao.com/")//设置网络请求的url地址
            .addConverterFactory(GsonConverterFactory.create())//设置数据解析器
            .build();
    //从上面看出：一个请求的URL可以通过替换块和请求方法的参数 来进行动态的URL更新
    //替换块是由被{}包裹起来的字符串构成
    //即：Retrofit支持动态改变网络请求根目录


//    @HTTP
//    作用：替换@GET、@POST、@PUT、@DELETE、@HEAD注解的作用 及 更多功能拓展
//    具体使用：通过属性method、path、hasBody进行设置

    /**
     * method：网络请求的方法（区分大小写）
     * path：网络请求地址路径
     * hasBody：是否有请求体
     */
    @HTTP(method = "GET", path = "blog/{id}", hasBody = false)
    Call<ResponseBody> getCall(@Path("id") int id);
    //{id}表示是一个变量
    //method的值retrofit不会做处理，所以要自行保证准确

    /**
     * 表明是一个表单格式的请求（Content-Type:application/x-www-form-urlencoded）
     * <code>Field("username")</code> 表示将后面的 <code>String name</code> 中name的取值作为 username 的值
     *
     * @param name
     * @param age
     * @return
     */
    @POST("/form")
    @FormUrlEncoded
    Call<ResponseBody> testFormUrlEncode(@Field("username") String name, @Field("age") int age);

    /**
     * Map的key作为表单的键
     *
     * @param map
     * @return
     */
    @POST("/form")
    @FormUrlEncoded
    Call<ResponseBody> testFormUrlEncode(@FieldMap Map<String, Object> map);

    /**
     * {@link Part} 后面支持三种类型，{@link RequestBody}、{@link okhttp3.MultipartBody.Part} 、任意类型
     * 除 {@link okhttp3.MultipartBody.Part} 以外，其它类型都必须带上表单字段({@link okhttp3.MultipartBody.Part} 中已经包含了表单字段的信息)，
     */
    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload(@Part("name") RequestBody name, @Part("age") RequestBody age, @Part MultipartBody.Part file);

    //具体使用
    GetRequest_Interface service = retrofit.create(GetRequest_Interface.class);
    //@FormUrlEncoded
    Call<ResponseBody> call1 = service.testFormUrlEncode("Carson", 24);
    //@FieldMap 实现效果与上面相同,但要传入Map
    Map<String, Object> map = new HashMap<>();
    //    map.put("username", "Carson");
//    map.put("age", 24);
    Call<ResponseBody> call2 = service.testFormUrlEncode(map);
    //@Multipart
//    RequestBody name = RequestBody.create(textType,"Carson");
//    RequestBody age = RequestBody.create(textType,"24");
//    MultipartBody.Part filePart = MultipartBody.Part.createFormData("file","qudao.txt",file);
//    Call<ResponseBody> call2 = service.testFileUpload(name,age,filePart);

    //@Header()
    @GET("user")
    Call<ResponseBody> getUser(@Header("Authorization") String authorization);

    @Headers("Authorization:authorization")
    @GET("user")
    Call<ResponseBody> getUser();
    //以上的效果是一致的
    //区别在于使用场景和使用方式
    //1.使用场景：@Header用于添加不固定的请求头,@Headers用于添加固定的请求头
    //2.使用方式：@Header作用于方法的参数,@Headers作用于方法

//    @Body
//    作用：以 Post方式 传递 自定义数据类型 给服务器
//    特别注意：如果提交的是一个Map，那么作用相当于 @Field
//    不过Map要经过 FormBody.Builder 类处理成为符合 Okhttp 格式的表单
//    if(map !=null){
//        for (Map.Entry<String, Object> entry : map.entrySet()) {
//            bodyBuilder.add(entry.getKey(), String.valueOf(entry.getValue()));
//        }
//    }


//    @Part & @PartMap
//    作用：发送 Post请求 时提交请求的表单字段
//    与@Field的区别：功能相同，但携带的参数类型更加丰富，包括数据流，所以适用于 有文件上传 的场景

    /**
     * {@link Part} 后面支持三种类型，{@link RequestBody}、{@link okhttp3.MultipartBody.Part} 、任意类型
     * 除 {@link okhttp3.MultipartBody.Part} 以外，其它类型都必须带上表单字段({@link okhttp3.MultipartBody.Part} 中已经包含了表单字段的信息)，
     */
    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload1(@Part("name") RequestBody name, @Part("age") RequestBody age, @Part MultipartBody.Part file);

    /**
     * PartMap 注解支持一个Map作为参数，支持 {@link RequestBody } 类型，
     * 如果有其它的类型，会被{@link retrofit2.Converter}转换，如后面会介绍的 使用{@link com.google.gson.Gson} 的 {@link retrofit2.converter.gson.GsonRequestBodyConverter}
     * 所以{@link MultipartBody.Part} 就不适用了,所以文件只能用<b> @Part MultipartBody.Part </b>
     */
    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload2(@PartMap Map<String, RequestBody> args, @Part MultipartBody.Part file);

    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload3(@PartMap Map<String, RequestBody> args);

    // 具体使用
    MediaType textType = MediaType.parse("text/plain");
    RequestBody name = RequestBody.create(textType, "Carson");
    RequestBody age = RequestBody.create(textType, "24");
    RequestBody file = RequestBody.create(MediaType.parse("application/octet-stream"), "这里是模拟文件的内容");

    // @Part
    MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "qudao.txt", file);
    Call<ResponseBody> call3 = service.testFileUpload1(name, age, filePart);
//        ResponseBodyPrinter.printResponseBody(call3);
//
//                // @PartMap
//                // 实现和上面同样的效果
//                Map<String, RequestBody> fileUpload2Args = new HashMap<>();
//        fileUpload2Args.put("name", name);
//        fileUpload2Args.put("age", age);
//        //这里并不会被当成文件，因为没有文件名(包含在Content-Disposition请求头中)，但上面的 filePart 有
//        //fileUpload2Args.put("file", file);
//        Call<ResponseBody> call4 = service.testFileUpload2(fileUpload2Args, filePart); //单独处理文件
//        ResponseBodyPrinter.printResponseBody(call4);

    //    @Query和@QueryMap
//    作用：用于 @GET 方法的查询参数（Query = Url 中 ‘?’ 后面的 key-value）
//        如：url = http://www.println.net/?cate=android，其中，Query = cate
    @GET("/")
    Call<String> cate(@Query("cate") String cate);

    //@Path
    //作用：URL地址的缺省值
    //具体使用：
    @GET("users/{user}/repos")
    Call<ResponseBody> getBlog(@Path("user") String user);
    // 访问的API是：https://api.github.com/users/{user}/repos
    // 在发起请求时， {user} 会被替换为方法的第一个参数 user（被@Path注解作用）

    //@Url
    //作用：直接传入一个请求的 URL变量 用于URL设置
    @GET
    Call<ResponseBody> testUrlAndQuery(@Url String url, @Query("showAll") boolean showAll);
    // 当有URL注解时，@GET传入的URL就可以省略
    // 当GET、POST...HTTP等方法中没有设置Url时，则必须使用 {@link Url}提供

    @GET("ajax.php?a=fy&f=auto&t=auto&w=follow%20system")
    Call<Translation> getCall1();

    @POST("translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen=&ssid=&network=&abtest=")
    @FormUrlEncoded
    Call<Translationyd> getCall(@Field("i") String targetSentence);
    //采用@Post表示Post方法进行请求（传入部分url地址）
    // 采用@FormUrlEncoded注解的原因:API规定采用请求格式x-www-form-urlencoded,即表单形式
    // 需要配合@Field使用

    //http://im.kencan.cc/app/love.php?i=10&c=entry&m=jy_ppp
    @POST("/app/love.php")
    @FormUrlEncoded
    Call<ResponseBody> getKencan(@FieldMap Map<String,Object> map);

    @POST("/app/love.php?i=10&c=entry&m=jy_ppp&do=andrioconfig")
    Call<ResponseBody> getKencan2();

    @GET("getIpInfo.php?ip=59.108.54.37")
    Call<IpModel> getIpMsg();
}
