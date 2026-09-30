package jp.co.sss.lms.ct.f06_login2;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト ログイン機能②
 * ケース17
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース17 受講生 初回ログイン 正常系")
public class Case17 {

	/** 前処理 */
	@BeforeAll
	static void before() {

		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		goTo("http://localhost:" + 8080 + "/lms");
		assertEquals("ログイン | LMS", webDriver.getTitle());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに初期登録された未ログインの受講生ユーザーでログイン")
	void test02() {
		// 存在するユーザーのログインIDを入力
		WebElement loginIdElement = webDriver.findElement(By.id("loginId"));
		loginIdElement.clear();
		loginIdElement.sendKeys("StudentAA01");
		// 2. 存在するユーザーのパスワードを入力
		WebElement passwordElement = webDriver.findElement(By.id("password"));
		passwordElement.clear();
		passwordElement.sendKeys("StudentAA01");

		//  ログインボタンをクリック
		webDriver.findElement(By.className("btn-primary")).click();

		WebElement titleElement = webDriver.findElement(By.tagName("h2"));
		String actualText = titleElement.getText();
		assertEquals("利用規約", actualText);

		getEvidence(new Object() {
		}, "rogin");
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 「同意します」チェックボックスにチェックを入れ「次へ」ボタン押下")
	void test03() {
		scrollBy("1000");
		webDriver.findElement(By.name("securityFlg")).click();
		webDriver.findElement(By.className("btn-primary")).click();

		getEvidence(new Object() {
		}, "clickinput");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 変更パスワードを入力し「変更」ボタン押下")
	void test04() {
		visibilityTimeout(By.tagName("h2"), 5);
		visibilityTimeout(By.id("currentPassword"), 5);
		// 存在するユーザーのログインIDを入力
		WebElement conrrentPasswordElement = webDriver.findElement(By.id("currentPassword"));
		conrrentPasswordElement.clear();
		conrrentPasswordElement.sendKeys("StudentAA01");
		// 2. 存在するユーザーのパスワードを入力
		WebElement passwordElement = webDriver.findElement(By.id("password"));
		passwordElement.clear();
		passwordElement.sendKeys("StudentAA0111");

		WebElement passwordConfirmElement = webDriver.findElement(By.id("passwordConfirm"));
		passwordConfirmElement.clear();
		passwordConfirmElement.sendKeys("StudentAA0111");
		WebElement updBtn = webDriver.findElement(By.id("upd-btn"));
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		js.executeScript("arguments[0].click();", updBtn);
		//  active クラスが表示されるまで5秒待機
		visibilityTimeout(By.className("active"), 5);
		// 要素を取得して検証＆エビデンス取得
		WebElement courseDetail = webDriver.findElement(By.className("active"));
		String actualText = courseDetail.getText();
		assertEquals("コース詳細", actualText);
		getEvidence(new Object() {
		}, "changePassword");
	}

}
