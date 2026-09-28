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
 * ケース06
 * @author holy
 * @param <faqCategoryList>
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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
		webDriver.findElement(By.partialLinkText("よくある質問")).click();
		//visibilityTimeout(By.cssSelector("form-horizontal"), 5);
		Object[] windowHandles = webDriver.getWindowHandles().toArray();
		webDriver.switchTo().window((String) windowHandles[1]);

		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		getEvidence(new Object() {
		}, "Question");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {
		//formを取得
		WebElement categoryElement = webDriver.findElement(By.partialLinkText("研修関係"));
		categoryElement.click();
		scrollTo("250");
		//検索結果のリストを取得
		List<WebElement> seachList = webDriver.findElements(By.cssSelector("tbody tr td dl"));
		//質問が一致しているかの確認
		assertEquals("Q.キャンセル料・途中退校について", seachList.getFirst().getText());
		assertEquals("Q.研修の申し込みはどのようにすれば良いですか？", seachList.getLast().getText());

		getEvidence(new Object() {
		}, "categorySearch");

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {
		//「研修関係」のリンクの要素を取得
		WebElement categoryElement = webDriver.findElement(By.partialLinkText("研修関係"));
		categoryElement.click();
		scrollTo("500");
		//検索結果のリストを取得
		List<WebElement> searchList = webDriver.findElements(By.cssSelector("tbody tr td dl"));
		//検索結果のリスト要素を順番にクリック
		for (WebElement element : searchList) {
			element.click();
		}
		//回答（A.）要素のリストを取得
		List<WebElement> answerList = webDriver.findElements(By.id("answer-h[${status.index}]"));
		//表示されている内容が正しいかの一致確認
		assertEquals("A. 受講者の退職や解雇等、やむを得ない事情による途中終了に関してなど、事情をお伺いした上で、協議という形を取らせて頂きます。 弊社営業担当までご相談下さい。",
				answerList.getFirst().getText());
		assertEquals(
				"A. 営業担当がいる場合は、営業担当までご連絡ください。 申し込み方法についてご案内させていただきます。 なお、弊社営業営業がいない場合は、東京ITスクール運営事務局までご連絡いただけると幸いです。",
				answerList.getLast().getText());
		getEvidence(new Object() {
		}, "SearchResult");

	}

}
