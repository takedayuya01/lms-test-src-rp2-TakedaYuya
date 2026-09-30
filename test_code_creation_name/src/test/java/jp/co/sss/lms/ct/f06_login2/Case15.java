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
import org.openqa.selenium.WebElement;

/**
 * 結合テスト ログイン機能②
 * ケース15
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース15 受講生 初回ログイン 利用規約に不同意")
public class Case15 {

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
	@DisplayName("テスト03 「同意します」チェックボックスにチェックをせず「次へ」ボタンを押下")
	void test03() {
		scrollBy("1000");
		webDriver.findElement(By.className("btn-primary")).click();

		visibilityTimeout(By.className("error"), 5);
		getEvidence(new Object() {
		}, "error");
	}

}
