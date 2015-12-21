package slim3.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.logging.Logger;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.AbstractValidator;
import org.slim3.controller.validator.Errors;
import org.slim3.controller.validator.Validators;
import org.slim3.util.ApplicationMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import slim3.controller.Const.RegexType;
import slim3.dto.JsonDto;
import slim3.dto.ManageUserDto;
import slim3.dto.MsUserDto;
import slim3.exception.MyException;
import slim3.meta.MsUserMeta;
import slim3.service.AuthService;
import slim3.service.datastore.ManageUserService;
import slim3.service.datastore.MenuPageService;
import slim3.service.datastore.MenuService;
import slim3.service.datastore.MsShopService;
import slim3.service.datastore.MsUserService;
import slim3.service.datastore.ReserveService;
import slim3.service.factory.ServiceFactory;
import slim3.service.tools.userManage.SetShopDefaultService;
import util.CookieUtil;
import util.StackTraceUtil;
import util.StringUtil;


public abstract class AbstractController extends Controller {

    // ================================================================
    // Logger
    public final static Logger log = Logger.getLogger(AbstractController.class.getName());
    
    // ================================================================
    // Model
    protected static MsUserMeta MS_USER_META = MsUserMeta.get();
    
    // ================================================================
    // Service
    /** Datastoreサービス */
    protected ManageUserDto manageUserDto = new ManageUserDto();
    protected ManageUserService manageUserService = new ManageUserService();
    protected MsUserDto msUserDto = new MsUserDto();
    protected JsonDto jsonDto = new JsonDto();
    
    protected MsUserService msUserService = new MsUserService();
    protected MenuPageService menuPageService = new MenuPageService();
    protected MenuService menuService = new MenuService();
    protected MsShopService msShopService = new MsShopService();
    protected ReserveService msReserveService = new ReserveService();
    
    protected SetShopDefaultService setShopDefaultService = new SetShopDefaultService();
    
    
    /** 認証サービス */
    protected AuthService authService = ServiceFactory.getService(AuthService.class);
    
    /**
     * パラメータをString型で受け取ります。
     */
    @Override
    protected String asString(CharSequence name) throws NullPointerException{
        if (name == null) {
            throw new NullPointerException("The name parameter must not be null.");
        }
        String str = StringUtil.toString(request.getAttribute(name.toString()));
        if(StringUtil.isNotEmpty(str)) {
            return StringUtil.trim(str);
        }
        return null;
    }
    
    /**
    *
    * @param <T>
    * @param Controllerで作成したValidators
    * @param パラメータ名
    * @param 最小文字列長
    * @param 最大文字列長
    * @param 必須かどうか
    * @param 正規表現にマッチしているかどうか
    * @param Enumに存在するかどうか
    */
   public <T> void validate(Validators validators, String name, int minlength, int maxlength, boolean required, RegexType regexType, Class<T> enumClss) {

       if(required) {
           validators.add(name, validators.required());
           validators.validate();
           // 空文字のみの場合はエラーにする。
           validators.add(name, validators.regexp("[\\s\\r\\n]*[\\S]+[\\S\\s\\r\\n]*"));
           validators.validate();
       }

       // 必須じゃなくて未入力の場合は以下のチェックはしない
       if(!required) {
           String value = asString(name);
           if(StringUtil.isEmpty(value)) {
               return;
           }
       }

       if(minlength != 0) {
           validators.add(name, validators.minlength(minlength));
           validators.validate();

       }
       if(maxlength != 0) {
           validators.add(name, validators.maxlength(maxlength));
           validators.validate();
       }
       if(null != regexType) {
           String[] values = regexType.getValues();
           for(String s : values) {
               validators.add(name, validators.regexp(s));
               if(!validators.validate()) {
                   Errors errors = validators.getErrors();
                   //TODO バリデートが上手くいかない。application_ja.properties
                   if(errors.containsValue("電話番号が不正です。")) {
                       if(s.equals(Const.REGEX_PHONE)) {
                           errors.put(name + "Sample", "左記のように入力してください。 例）090-1234-5678");
                       }
                   }
                   if(errors.containsValue("メールアドレスが不正です。")) {
                       if(s.equals(Const.REGEX_MAIL_ADDRESS)) {
                           errors.put(name + "Sample", "左記のように入力してください。 例）sample@sample.com");
                       }
                   }
               }
           }
       }
       // Enumのバリデータ
       if(enumClss != null) {
           if(required) {
               AbstractController.EnumRequiredValidator<T> enumRequiredValidator = new AbstractController.EnumRequiredValidator<T>(enumClss);
               validators.add(name, enumRequiredValidator);
               validators.validate();

           } else {
               AbstractController.EnumValidator<T> enumValidator = new AbstractController.EnumValidator<T>(enumClss);
               validators.add(name, enumValidator);
               validators.validate();
           }
       }

   }
   
   /**
    * Enumのバリデータです。 パラメータのNullの場合は無視します。
    *
    * @author kitazawa.takuya
    *
    * @param <T>
    */
   public static class EnumValidator<T> extends AbstractValidator {

       private Class<T> clazz;

       public EnumValidator(Class<T> c) {
           clazz = c;
       }

       public EnumValidator(Class<T> c, String m) {
           this(c);
           message = m;
       }

//       @Override
       public String validate(Map<String, Object> parameters, String name) {

           // パラメータがない場合はチェックをスキップします。
           Object value = parameters.get(name);
           if(StringUtil.isEmpty((String) value)) {
               return null;
           }

           boolean flg = false;
           T[] enumConstants = clazz.getEnumConstants();

           for(T t : enumConstants) {
               if(t.toString().equals(value)) {
                   flg = true;
                   break;
               }
           }

           if(flg) {
               return null;
           }

           if(message != null) {
               return message;
           }
           return ApplicationMessage.get(getMessageKey(), getLabel(name));
       }

       @Override
       protected String getMessageKey() {
           return "validator.enumType";
       }
   }
   
   /**
    * Enumのバリデータです。 パラメータ必須時に利用します。
    *
    * @author kitazawa.takuya
    *
    * @param <T>
    */
   public static class EnumRequiredValidator<T> extends AbstractValidator {

       private Class<T> clazz;

       public EnumRequiredValidator(Class<T> c) {
           clazz = c;
       }

       public EnumRequiredValidator(Class<T> c, String m) {
           this(c);
           message = m;
       }

//       @Override
       public String validate(Map<String, Object> parameters, String name) {

           Object value = parameters.get(name);
           if(StringUtil.isEmpty((String) value)) {
               return ApplicationMessage.get("validator.enumTypeNone", getLabel(name));
           }

           boolean flg = false;
           T[] enumConstants = clazz.getEnumConstants();

           for(T t : enumConstants) {
               if(t.toString().equals(value)) {
                   flg = true;
                   break;
               }
           }

           if(flg) {
               return null;
           }

           if(message != null) {
               return message;
           }
           return ApplicationMessage.get(getMessageKey(), getLabel(name));
       }

       @Override
       protected String getMessageKey() {
           return "validator.enumType";
       }
   }
   
   /**
    * スタックトレースをログに出力します。
    *
    * @param e
    */
   protected void writeErrorLog(Throwable e) {
       log.warning(StackTraceUtil.toString(e));
   }
   
   /**
    * リクエストしたURLを保持して、ログイン画面を返却します。
    * @return 
    */
   protected Navigation showLoginPage() {
       String requestURI = request.getRequestURI();
       //セッションにリクエストURLを保存します。
       CookieUtil.setCookie(response, Const.MS_REQUEST_URL, requestURI, 3600);
       log.info("リクエストURL保存：" + requestURI);
       return redirect("/tools/userManage/login");
   }
    
   
   
   /**
    * 返却用のJSONオブジェクトを生成します。
    *
    * @param status
    * @param msg
    * @param obj
    * @return JsonDto
    */
   protected JsonDto createJsonDto(String status, String msg, Object obj){
       jsonDto.setStatus(status);
       if(StringUtil.isNotEmpty(msg)) {
           jsonDto.setMsg(msg);
       }
       if(obj != null) {
           jsonDto.setObj(obj);
       }

       return jsonDto;
       
   }

   
   /**
    * 文字列をそのままレスポンスとして返却します。
    *
    * @param str
    * @return
    */
   protected String toJson(Object obj) {
       ObjectMapper mapper = new ObjectMapper();

       try {
//           return mapper.writeValueAsString(obj);
           //インデントありで出力
           return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
           
       } catch (JsonProcessingException e) {
           e.printStackTrace();
           log.warning(StackTraceUtil.toString(e));
           throw new MyException("シリアライズ中にエラーが発生しました。");
       }
   }
       
   /**
    * レスポンスのセッティングを行います。
    * @param bytes
    * @return
    */
   private Navigation write(byte[] bytes) {
       try {
           response.setCharacterEncoding(Const.DEFAULT_CONTENT_TYPE);
           response.setContentType(Const.JSON_CONTENT_TYPE);
           response.setContentLength(bytes.length);
           //バイナリデータを出力するためのストリームを取得する
           response.getOutputStream().write(bytes);
       } catch (IOException e) {
           throw new MyException(e);
       }
       try {
           response.flushBuffer();
       } catch (IOException e) {
           throw new MyException(e);
       }
       return null;
   }
   
   /**
    * JSON形式でレスポンスを返却します。
    *
    * @param errors
    * @return
    */
   protected Navigation returnResponse(JsonDto jsonDto) {
       byte[] bytes = toJson(jsonDto).getBytes(Charset.forName(Const.DEFAULT_CONTENT_TYPE));
       return write(bytes);
   }
    
}




