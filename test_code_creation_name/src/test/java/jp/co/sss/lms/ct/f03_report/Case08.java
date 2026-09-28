package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		WebElement loginIdElement = webDriver.findElement(By.id("loginId"));
		loginIdElement.clear();
		loginIdElement.sendKeys("StudentAA01");
		// 2. 存在するユーザーのパスワードを入力
		WebElement passwordElement = webDriver.findElement(By.id("password"));
		passwordElement.clear();
		passwordElement.sendKeys("StudentAA0111");

		//  ログインボタンをクリック
		webDriver.findElement(By.className("btn-primary")).click();
		// 1. active クラスが表示されるまで5秒待機
		visibilityTimeout(By.className("active"), 5);

		// 2. 要素を取得して検証＆エビデンス取得
		WebElement courseDetail = webDriver.findElement(By.className("active"));
		String actualText = courseDetail.getText();
		assertEquals("コース詳細", actualText);

		getEvidence(new Object() {
		}, "rogin");
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {

		List<WebElement> rowList = webDriver.findElements(By.cssSelector("td.w10per"));

		for (WebElement row : rowList) {
			String rowTextString = row.getText();
			if (rowTextString.equals("未提出")) {
				WebElement clickElement = webDriver.findElement(By.cssSelector("input[type='submit']"));
				clickElement.click();
				break;
			}
		}
		getEvidence(new Object() {
		}, "input");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// TODO ここに追加
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		// TODO ここに追加
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
		// TODO ここに追加
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		// TODO ここに追加
	}

}
