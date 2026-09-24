package jp.co.sss.lms.ct.f01_login1;

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
import org.openqa.selenium.WebElement;

/**
 * 結合テスト ログイン機能①
 * ケース03
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース03 受講生 ログイン 正常系")
public class Case03 {

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

		// コンテキストパス /lms を含めたURLを指定
		String url = "http://localhost:" + 8080 + "/lms";
		webDriver.get(url);
		assertEquals("ログイン | LMS", webDriver.getTitle());
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {

		String url = "http://localhost:" + 8080 + "/lms";
		webDriver.get(url);
		// 存在するユーザーのログインIDを入力
		WebElement loginIdElement = webDriver.findElement(By.id("loginId"));
		loginIdElement.clear();
		loginIdElement.sendKeys("StudentAA01");
		// 2. 存在するユーザーのパスワードを入力
		WebElement passwordElement = webDriver.findElement(By.id("password"));
		passwordElement.clear();
		passwordElement.sendKeys("StudentAA011");

		getEvidence(new Object() {
		}, "input");

		//  ログインボタンをクリック
		webDriver.findElement(By.className("btn-primary")).click();
		getEvidence(new Object() {
		}, "rogin");
	}

}
