package input;

import javafx.scene.input.KeyCode;

/**
 * 
 * @author 王浩天
 * @version	2020.12.23
 * @inherit 
 * @functions 键盘按键枚举类，提供常用的键盘上常用键的枚举
 * @properties Key.xxx ...
 * @methods 
 * 		getKeyCode(): 获取键码
 * 		getKeyText(): 获取键符
 */

public enum Key { // 键枚举类，用于表示键盘上的各种常用键的键码和键符
	
	// 数字键
	NUM0(KeyCode.DIGIT0,"0"), // 数字0
	NUM1(KeyCode.DIGIT1,"1"), // 数字1
	NUM2(KeyCode.DIGIT2,"2"), // 数字2
	NUM3(KeyCode.DIGIT3,"3"), // 数字3
	NUM4(KeyCode.DIGIT4,"4"), // 数字4
	NUM5(KeyCode.DIGIT5,"5"), // 数字5
	NUM6(KeyCode.DIGIT6,"6"), // 数字6
	NUM7(KeyCode.DIGIT7,"7"), // 数字7
	NUM8(KeyCode.DIGIT8,"8"), // 数字8
	NUM9(KeyCode.DIGIT9,"9"), // 数字9
	
	// 字母键
	A(KeyCode.A,"A"), // 字母A
	B(KeyCode.B,"B"), // 字母B
	C(KeyCode.C,"C"), // 字母C
	D(KeyCode.D,"D"), // 字母D
	E(KeyCode.E,"E"), // 字母E
	F(KeyCode.F,"F"), // 字母F
	G(KeyCode.G,"G"), // 字母G
	H(KeyCode.H,"H"), // 字母H
	I(KeyCode.I,"I"), // 字母I
	J(KeyCode.J,"J"), // 字母J
	K(KeyCode.K,"K"), // 字母K
	L(KeyCode.L,"L"), // 字母L
	M(KeyCode.M,"M"), // 字母M
	N(KeyCode.N,"N"), // 字母N
	O(KeyCode.O,"O"), // 字母O
	P(KeyCode.P,"P"), // 字母P
	Q(KeyCode.Q,"Q"), // 字母Q
	R(KeyCode.R,"R"), // 字母R
	S(KeyCode.S,"S"), // 字母S
	T(KeyCode.T,"T"), // 字母T
	U(KeyCode.U,"U"), // 字母U
	V(KeyCode.V,"V"), // 字母V
	W(KeyCode.W,"W"), // 字母W
	X(KeyCode.X,"X"), // 字母X
	Y(KeyCode.Y,"Y"), // 字母Y
	Z(KeyCode.Z,"Z"), // 字母Z
	
	// 功能键
	F1(KeyCode.F1,"F1"), // 功能F1
	F2(KeyCode.F2,"F2"), // 功能F2
	F3(KeyCode.F3,"F3"), // 功能F3
	F4(KeyCode.F4,"F4"), // 功能F4
	F5(KeyCode.F5,"F5"), // 功能F5
	F6(KeyCode.F6,"F6"), // 功能F6
	F7(KeyCode.F7,"F7"), // 功能F7
	F8(KeyCode.F8,"F8"), // 功能F8
	F9(KeyCode.F9,"F9"), // 功能F9
	F10(KeyCode.F10,"F10"), // 功能F10
	F11(KeyCode.F11,"F11"), // 功能F11
	F12(KeyCode.F12,"F12"), // 功能F12
	
	// 空白键
	SPACE(KeyCode.SPACE," "), // 空格
	ENTER(KeyCode.ENTER, "\n"), // 回车
	TAB(KeyCode.TAB,"\t"), // 制表符
	
	// 符号键
	WAVE(null,"~"), // 波浪线
	EXCLAMATION(KeyCode.EXCLAMATION_MARK,"!"), // 感叹号
	AT(KeyCode.AT,"@"), // 艾特符
	SHARP(KeyCode.NUMBER_SIGN,"#"), // 井号
	DOLLAR(KeyCode.DOLLAR,"$"), // 美元符
	PERCENT(null,"%"), // 百分号
	CIRCUMFLEX(KeyCode.CIRCUMFLEX,"^"), // 尖号
	AND(KeyCode.AMPERSAND,"&"), // 连接符
	OR(null,"|"), // 竖线
	STAR(KeyCode.ASTERISK,"*"), // 星号
	UNDERSCORE(null,"_"), // 下划线
	
	COLON(KeyCode.COLON,":"), // 冒号
	SEMICOLON(KeyCode.SEMICOLON,";"), // 分号
	QUESTION(null,"?"), // 问号
	
	QUOTE(KeyCode.QUOTE,"'"), // 单引号
	DBL_QUOTE(KeyCode.QUOTEDBL,"\""), // 双引号
	BACK_QUOTE(KeyCode.BACK_QUOTE,"`"), // 反引号
	
	COMMA(KeyCode.COMMA,","), // 逗号
	PERIOD(KeyCode.PERIOD,"."), // 句号
	
	SLASH(KeyCode.SLASH,"/"), // 斜杠
	BACK_SLASH(KeyCode.BACK_SLASH,"\\"), // 反斜杠
	
	PLUS(KeyCode.PLUS,"+"), // 加号
	MINUS(KeyCode.MINUS,"-"), // 减号
	EQUALS(KeyCode.EQUALS,"="), // 等号
	LESS(KeyCode.LESS,"<"), // 小于号
	GREATER(KeyCode.GREATER,">"), // 大于号
	
	LEFT_PARENTHESIS(KeyCode.LEFT_PARENTHESIS,"("), // 左小括号
	RIGHT_PARENTHESIS(KeyCode.RIGHT_PARENTHESIS,")"), // 右小括号
	LEFT_BRACKET(KeyCode.OPEN_BRACKET,"["), // 左中括号
	RIGHT_BRACKET(KeyCode.CLOSE_BRACKET,"]"), //右中括号
	LEFT_BRACE(KeyCode.BRACELEFT,"{"), // 左大括号
	RIGHT_BRACE(KeyCode.BRACERIGHT,"}"), // 右大括号
	
	UP(KeyCode.UP,"UP"), // 上箭头
	DOWN(KeyCode.DOWN,"DOWN"), // 下箭头
	LEFT(KeyCode.LEFT,"LEFT"), // 左箭头
	RIGHT(KeyCode.RIGHT,"RIGHT"), // 右箭头
	
	// 组合键
	CONTROL(KeyCode.CONTROL,"CTRL"), // control键
	SHIFT(KeyCode.SHIFT,"SHIFT"), // shift键
	ALT(KeyCode.ALT,"ALT"), // alt键
	WINDOWS(KeyCode.WINDOWS,"WIN"), // windows键
	COMMAND(KeyCode.COMMAND,"CMD"), // command键
	MENU(KeyCode.CONTEXT_MENU,"MENU"), // menu键
	CAPS_LOCK(KeyCode.CAPS,"CAPS_LOCK"), // 大小写锁
	NUM_LOCK(KeyCode.NUM_LOCK,"NUM_LOCK"), // 数字锁
	SCROLL_LOCK(KeyCode.SCROLL_LOCK,"SCROLL_LOCK"), // 滚屏锁
	
	// 编辑键
	BACK_SPACE(KeyCode.BACK_SPACE,"BACK_SPACE"), // 退格键
	INSERT(KeyCode.INSERT,"INSERT"), // 插入键
	DELETE(KeyCode.DELETE,"DELETE"), // 删除键
	HOME(KeyCode.HOME,"HOME"), // home键
	END(KeyCode.END,"END"), // 结束键
	PAGE_UP(KeyCode.PAGE_UP,"PAGE_UP"), // 上页键
	PAGE_DOWN(KeyCode.PAGE_DOWN,"PAGE_DOWN"), // 下页键
	PAUSE(KeyCode.PAUSE,"PAUSE"), // 暂停键
	PRINTSCREEN(KeyCode.PRINTSCREEN,"PRTSCR"), // 打印键
	ESCAPE(KeyCode.ESCAPE,"ESC"), // 结束键
	
	// 小键盘数字键
	NUMPAD0(KeyCode.NUMPAD0,"0"), // 数字0
	NUMPAD1(KeyCode.NUMPAD1,"1"), // 数字1
	NUMPAD2(KeyCode.NUMPAD2,"2"), // 数字2
	NUMPAD3(KeyCode.NUMPAD3,"3"), // 数字3
	NUMPAD4(KeyCode.NUMPAD4,"4"), // 数字4
	NUMPAD5(KeyCode.NUMPAD5,"5"), // 数字5
	NUMPAD6(KeyCode.NUMPAD6,"6"), // 数字6
	NUMPAD7(KeyCode.NUMPAD7,"7"), // 数字7
	NUMPAD8(KeyCode.NUMPAD8,"8"), // 数字8
	NUMPAD9(KeyCode.NUMPAD9,"9"), // 数字9
	
	// 小键盘符号键
	NUMPAD_PLUS(KeyCode.ADD,"+"), // 加号
	NUMPAD_MINUS(KeyCode.SUBTRACT,"-"), // 减号
	NUMPAD_MULTIPLY(KeyCode.MULTIPLY,"*"), // 乘号
	NUMPAD_DIVIDE(KeyCode.DIVIDE,"/"), // 除号
	NUMPAD_DECIMAL(KeyCode.DECIMAL,"."), // 小数点
	
	NUMPAD_UP(KeyCode.KP_UP,"UP"), // 上箭头
	NUMPAD_DOWN(KeyCode.KP_DOWN,"DOWN"), // 下箭头
	NUMPAD_LEFT(KeyCode.KP_LEFT,"LEFT"), // 左箭头
	NUMPAD_RIGHT(KeyCode.KP_RIGHT,"RIGHT"), // 右箭头

	;
	
	private final KeyCode code; // 键码
	private final String text; // 键符
	
	// 初始化
	private Key(KeyCode code, String text) {
		this.code = code;
		this.text = text;
	}
	
	// Getter
	public KeyCode getCode() { // 获取键码
		return code;
	}
	
	public String getText() { // 获取键符
		return text;
	}
	
	// 查找键
	public static Key find(KeyCode code) { // 按键码查找
		for(Key k : values()) {
			if(k.code != null && k.code == code) {
				return k;
			}
		}
		return null;
	}
	
	public static Key find(String text) { // 按键符查找
		for(Key k : values()) {
			if(k.text != null && k.text.equalsIgnoreCase(text)) { // 忽略大小写
				return k;
			}
		}
		return null;
	}
	
}
