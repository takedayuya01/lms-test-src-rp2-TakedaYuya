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
 * ケース02
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {

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
		// TODO ここに追加
		// コンテキストパス /lms を含めたURLを指定
		String url = "http://localhost:" + 8080 + "/lms";
		webDriver.get(url);
		//タイトルの検証
		assertEquals("ログイン | LMS", webDriver.getTitle());
		//エビデンス取得
		getEvidence(new Object() {
		}, "Case01_test02");

	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() {
		// TODO ここに追加
		String url = "http://localhost:" + 8080 + "/lms";
		webDriver.get(url);
		// 存在しないユーザーのログインIDを入力
		WebElement loginIdElement = webDriver.findElement(By.id("loginId"));
		loginIdElement.clear();
		loginIdElement.sendKeys("StudentAA777");
		// 2. 存在しないユーザーのパスワードを入力
		WebElement passwordElement = webDriver.findElement(By.id("password"));
		passwordElement.clear();
		passwordElement.sendKeys("StudentAA777");
		//  ログインボタンをクリック
		webDriver.findElement(By.className("btn-primary")).click();
		// エラー要素が表示されるまで待つ処理を追加
		visibilityTimeout(By.cssSelector(".help-inline.error"), 5);
		WebElement errorElement = webDriver.findElement(By.className("error"));
		assertEquals("* ログインに失敗しました。", errorElement.getText());
		//エビデンス（画面スクリーンショット）を取得
		getEvidence(new Object() {
		}, "Case01_test02_errorMsg");

	}

}
