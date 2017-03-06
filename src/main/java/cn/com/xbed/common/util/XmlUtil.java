package cn.com.xbed.common.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YUAN on 2016/9/6.
 * XML工具类
 */
public class XmlUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(XmlUtil.class);
    
    /**
     * 增加xml的声明头
     * @param text 文本
     * @return
     */
    private static String addXmlDeclare(String text) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + text;
    }

    public static List<String> getNodesByName(String text) {
        List<String> ret = new ArrayList<>();
        if(null == text || text.trim().equals("")) {
            return ret;
        }
        { //遇到<才开始解析
            String tag = "<";
            if(text.contains(tag)) {
                int index = text.indexOf(tag);
                text = text.substring(index);
            }
        }
        {
            String tag = "</ul>";
            if (text.contains(tag)) { //供测试环境使用
                text = text.substring(0, text.indexOf(tag) + tag.length());
            }
        }
        { //以<ul>为开始，却不以</ul>结尾的
            String prefix = "<ul>";
            String suffix = "</ul>";
            if(text.startsWith(prefix) && !text.endsWith(suffix)) {
                text += suffix;
            }
        }
        if(!text.contains("<span>")) {
            ret.add(text);
            return ret;
        }
        try {
            Document document = DocumentHelper.parseText(addXmlDeclare(text));
            Element root = document.getRootElement();
            getNodesByName(root, ret);
            return ret;
        } catch (DocumentException e) {
            logger.error(e.getMessage());
        }
        ret.add(text);
        return ret;
    }
    
    public static List<String> getNodes4Traffic(String text) {
        List<String> ret = new ArrayList<>();
        if(null == text || text.trim().equals("")) {
            return ret;
        }
        String [] lis = text.split("<li>");
        for(int i = 0; i < lis.length; i++) {
            String li = lis[i];
            li = replace4Space(li);
            lis[i] = li;
            if(!li.contains("<ul>")) {
                ret.add(li);
            }
        }
        
        return ret;
    }
    
    private static String replace4Space(String text) {
        text = text.replace("<li>", "").replace("</li>", "").replace("<span>", "").replace("</span>", "：").replace("</ul>", "");
        if(text.trim().startsWith("：")) {
            text = text.replace("：", "");
        }
        return text;
    }
    
    public static List<String> getNodesByName(Element root, List<String> text) {
        List nodes = root.elements();
        for(int i = 0; i < nodes.size(); i++) {
            Element element = (Element)nodes.get(i);
            if(element.getName().equals("span")) {
                text.add(element.getTextTrim());
            }
            getNodesByName(element, text);
        }
        return text;
    }

    public static void main(String[] args) {
        //String text = "<ul><li><i>1</i><span>受微信支付限制，单笔支付上限为3000元，若您需预订多晚且房费超过3000元，请分开预订，给你造成不便还请谅解；</span></li><li><i>2</i><span>目前仅支持接待持有中国第二代身份证的客人入住； </span></li><li><i>3</i><span>入住当日，待房间清洁完毕即可进入“我的订单”办理入住； </span></li><li><i>4</i><span>房门密码于退房当日12：00失效，特别情况请致电客服4006099222； </span></li><li><i>5</i><span>本房间不可携带宠物入内，还请谅解；</span></li><li><i>6</i><span>请爱惜房内物品，损坏需照价赔偿。</span></li></ul>物业安保设施完善尽责，24小时门禁管理，请在入住时持有效证件咨询物业保安并登记";
        //String text = "我是一个兵";
//        String text = "<ul><li><i>1</i><span>高端核心商圈：广州CBD珠江新城马场路</span></li><li><i>2</i><span>丰富完善配套：周边众多酒吧、餐厅、星巴克、银行、会所一应俱全；</span></li><li><i>3</i><span>精致装修设计：房间具有精致的装修设计，典雅舒适；</span></li>";
//        for(String str : getNodesByName(text)) {
//            System.out.println(str);
//        }
        String text = "<ul><li><span>停车场</span>世纪金花购物广场停车场/南门外广场停车场/南门外亚朵酒店停车场</li><li><span>资费情况</span>3元/2元/3元 每小时</li></ul>";
        List<String> strs = getNodes4Traffic(text);
        for(String str : strs) {
            System.out.println(str);
        }
    }
}
