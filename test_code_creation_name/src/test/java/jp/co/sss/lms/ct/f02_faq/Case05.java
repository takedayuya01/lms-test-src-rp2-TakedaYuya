package jp.co.sss.lms.ct.f02_faq;

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
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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

		// 存在するユーザーのログインIDを入力
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
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		//機能リンクをクリック
		visibilityTimeout(By.linkText("機能"), 5);
		webDriver.findElement(By.linkText("機能")).click();

		// その中からaタグの「ヘルプ」リンクをクリック
		visibilityTimeout(By.linkText("ヘルプ"), 5);
		webDriver.findElement(By.linkText("ヘルプ")).click();

		// タイトル 一致確認
		assertEquals("ヘルプ | LMS", webDriver.getTitle());

		getEvidence(new Object() {
		}, "help");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		//「よくある質問」リンクをクリック
		webDriver.findElement(By.linkText("よくある質問")).click();
		//別のタブへ移動
		Object[] windowHandles = webDriver.getWindowHandles().toArray();
		webDriver.switchTo().window((String) windowHandles[1]);
		// タイトル 一致確認
		visibilityTimeout(By.tagName("h2"), 5);
		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		getEvidence(new Object() {
		}, "Question");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		//formを取得
		WebElement writeElement = webDriver.findElement(By.id("form"));
		writeElement.clear();
		//取得したformに"研修"と入力
		writeElement.sendKeys("研修");
		//入力したしている文字が"研修"と一致しているかの確認
		assertEquals("研修", writeElement.getAttribute("value"));
		//提出ボタンをクリック
		webDriver.findElement(By.cssSelector("input[type='submit']")).click();
		visibilityTimeout(By.cssSelector("tbody tr td dl"), 5);
		//検索結果のリストを取得
		List<WebElement> searchList = webDriver.findElements(By.cssSelector("tbody tr td dl"));
		//検索結果が見えるようスクロール
		scrollTo("1000");

		for (WebElement element : searchList) {
			//画面上のすべての要素から値を取得
			String fullText = element.getAttribute("textContent");
			//研修の文字が含まれているかの確認
			assertTrue(fullText.contains("研修"));
		}

		getEvidence(new Object() {
		}, "keywordSearch");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		//formを取得
		WebElement writeElement = webDriver.findElement(By.id("form"));
		writeElement.clear();
		//研修と入力を行う
		writeElement.sendKeys("研修");
		getEvidence(new Object() {
		}, "keyword");
		//クリアボタンの要素を取得
		WebElement clearElement = webDriver.findElement(By.cssSelector("input[type='button']"));
		clearElement.click();
		//空文字になっているか確認
		assertEquals("", writeElement.getAttribute("value"));

		getEvidence(new Object() {
		}, "clear");
	}

}
