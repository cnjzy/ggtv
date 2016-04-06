package cn.oneweone.video.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import cn.oneweone.video.R;
import cn.oneweone.video.activity.adapter.VideoListAdapter;
import cn.oneweone.video.base.BaseActivity;
import cn.oneweone.video.common.Constants;
import cn.oneweone.video.util.DialogUtil;
import cn.oneweone.video.util.DialogUtil.OnAlertSelectId;

public class VideoListActivity extends BaseActivity {
	private ListView list;
	private String[] titlelist1 = { "乘机干了落入厕所陷阱的多名制服美女【12:10】",
			"【福利】佐伯ゆきな【13:30】", "【福利】丰满色女诱惑【13:40】", "【福利】气质熟妇【13:18】",
			"好色的日常102~あみな01", "志向 0028 敏感になってます 真衣【10:34】",
			"巨乳な清純 清水理紗【06:05】", "妖豔混亂開放S級女優極好身體恥辱樣子【13:22】",
			"巨乳な清純 清水理紗【29:01】", "美腳！短褲尻癡女逆泡妞【06:06】", "エッチな4610 青木 美愛【10:23】",
			"【福利】沙发上继续抠【20:34】", "如此美女先插为敬【05:00】",
			"無料エロ動画 has a hot rack and tight pussy【05:00】",
			"純白美肌のエロお嬢様 連続中出し 酒井ももか【07:59】", "【福利】就这样被你征服【08:52】",
			"【福利】偷情【19:54】", "-～小さい頃からコンプレックスだったHカップ～ 北山かんな【20:00】",
			"-メス豚生徒の父親に犯された女教師 長崎宏美【10:02】", "人斬り桃尻 ～もちもちの柔らかいお尻を弄ぶ～サリ【10:00】",
			"宾馆双飞两熟女【04:57】", "-チンコを加えてガチオナニーで逝きまくり！他人妻調教中！【10:01】",
			"【福利】强上良家【06:02】", "【福利】办公室的黑丝OL【15:03】",
			"家教姐姐在我床上睡著了,于是再也忍不住【25:38】", "角色扮演空姐，激發性欲【10:06】",
			"強制輪奸美巨乳妹妹系黑絲偶像【5:03】", "快感FUCK神奇白板少女失禁大量潮吹 無毛美少女【11:27】",
			"这个身材的女人最爽【13:25】", "花钱找的就是卖力【15:08】", "隔壁少妇勾引【25:01】",
			"吃大黑木耳【31:22】", "熟睡的女友开干【12:19】", "和兄弟一起双飞【12:08】" };
	private String[] titlelist2 = { "大山美名子,村瀬沙绪里,立花奈央,木下智子（精选版）",
			"發情女和叔叔禁斷之關係，極好色情調味品~ 美里麻衣", "淫亂的有錢人大量潮吹 優木あ",
			"经典珍藏多年的人妻熟女,舌技風俗嬢級理容師（精选版）", "女大生入店行竊失風 リエ[vip967]",
			"人妻調教 沖田千賀子[vip967]", "最新Zipang_12537-○本木円光神話 18歳 大好変態女編 [vip819]",
			"女体拷问研究所 ～突击！怒の女搜查官、完全凌辱拷问に陷落", "美丽可爱的礼物,松坂大辅是行当中她的嘴",
			"胖乎乎的小鸡泰莎享有公鸡在狗的风格", "东方在鹿腿画廊靴子手指&拉撒大Belt-on的农奴",
			"最新1000人斬 160201monami 淫亂妄想欲情~モナミ[22366]",
			"G-Queen 无毛宣言 一个可爱的妻子? 铃木", "傳説中的美熟女 赤坂ルナLuna", "突然決定AV出演  一ノ瀬麗花",
			"CAPPV-022015_108 17名女優之爆乳女優240分鐘",
			"一本道 062013_612 綺麗之顏降白色暴風雨 横山みれい",
			"最新heydouga-4030-1804-篠田あゆみ -感謝鑑賞之旅!! ", "都內の某企業受付嬢",
			"heyzo 0290 色狼俱樂部～淫亂OL平時的樂趣 綾瀬ティアラ",
			"MXGS-682电击复活！！麻生希 肉食系エログラマラス", "加勒比 062513-368 真實的H話題23 瑠菜",
			"工作的地方媽媽派遣員人妻姑娘編輯:三上春奈", "最新HEYZO 1074 素人娘生中出~西川紗菜~22366",
			"Tokyo Hot n0933 竹內汐音 3穴串刺阿鼻叫喚",
			"heyzo-0346-極好苗條身體 白白嫩嫩青筋若現 大美女雨宮琴音", "经典珍藏多年的Ｇ爆乳,成人芭蕾舞（怀旧版）",
			"Carib-022115-813 天然美女免費搭便車！惨遭失身之痛  西野あこ",
			"工作的媽媽手指尖兒巧妙理髮師:虻川ゆうり(中文字幕)", "融化我黏糊糊的愛液，憧憬的家庭教師- 雨宮琴音",
			"熟女系鼻祖【友田真希】大全集 海外流出版", "最新ASIA天國 0619 欲情する敏感OL~長谷川 梨江",
			"pacopacomama 上玉美熟女初撮影恥姿~森山愛子", "餌食牝 野口菜々木",
			"结城花梨 美讲师大战三黑汉强制潮喷射 【中文字幕】", "Tokyo-Hot n0780 藤原遼子 初アナル!藤原遼子2穴壊姦" };
	private String[] titlelist3 = { "95年的嫩妹國語對白完整版 稍微見過幾面的漂亮語文老師",
			"浴衣四十路奧樣中出~小林沙希[vip967]",
			"店長推薦作品(SMD-131)S Model 131 制服黑髮美女 : 金井みお[22366]",
			"最新 在酒店被潜规则的性感少妇[22366]", "最新熟女倶楽部 6087-ドS熟女のM男 後編 [vip819]",
			"被教练强上【32:12】 ", "美熟女上司被下属乱搞", "客室乘务员肤白逼美黑丝美女被20人陵辱",
			"【福利】室外拷着啪啪啪", "【福利】监狱中的凌辱", "人妻穿着黑丝被调教", "【福利】人妖还需要女人吗？",
			"穿上衣服在做太有才了", "亚裔女子与光头男的绳艺", "变态泌尿器科性交诊所 调教愿望 变得赤裸的M的性欲～",
			"双腿被捆绑的少妇被黑人强制", "【福利】对人体了解透彻的女医生就喜欢自己玩",
			"妇产科医生的电流痉挛绝顶纯粹中出01【19:54】",
			"【原创】欧美 金髪天国 诱の巨乳BIG TITS AMY BROOKEVS日本男儿",
			"宝贝,公司的身体被绑紧在谷仓和悬浮在空气中的深喉迷恋", "胸部丰满的女孩与肌肉的家伙strapon旋塞和骑着迪克",
			"麦迪逊斯科特的完美的身体由客人在军械库聚会上使用和滥用", "大陸高級會所,情侶玩黑絲制服誘惑 角色扮演 頂級誘惑",
			"原本要与女友玩sm的绳索先将好友老婆捆起来搞了", "成熟的奴隶女孩在她的膝盖deepthroating旋塞",
			"人妖布鲁纳FACEFUCKS她的男友角质", "大陸韓日打炮手微拍精選 Vol.26 美臀極度誘惑",
			"经典珍藏多年的女同志神物OL白書-女白领", "老板叫女秘书到办公室帮他消消火",
			"怪癖domina和她的螺栓在热femdom视频",
			"最新Muramura.tv 013016_345-捆綁女子感覺良好 大塚はな",
			"经典珍藏多年的水嶋あずみ 連褲襪系列之芭蕾舞教室（剧情版）", "美魔女 漂亮的接待 美少女男人誘惑",
			"最新Mesubuta 160129_1022_01-女子校生縛体罰 篠原祐子", "超美护士雾岛奈津美 连医生都忍不住了" };

	private VideoListAdapter mDetailAdapter;


	@Override
	public void initLayout() {
		setContentView(R.layout.activity_video_list);
	}

	@Override
	public void init() {
	}

	@Override
	public void initView() {
		list = ((ListView) findViewById(R.id.list));
	}

	@Override
	public void initListener() {
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				DialogUtil.showPay3Dialog(context, new OnAlertSelectId(){
    				public void onClick(int pay_type, Object o) {
    					int amount = Constants.VIP_TENURE;
    					int member_type = Constants.MEMBER_TYPE_IS_TENURE;
    					getOrder(amount, pay_type, member_type);
    				}
    			}, Constants.VIP_TENURE);
			}
		});
	}

	@Override
	public void initValue() {
		int i = getIntent().getExtras().getInt("clickposition");
		switch (i) {
		case 0:
			mDetailAdapter = new VideoListAdapter(this, titlelist1);
			break;
		case 1:
			mDetailAdapter = new VideoListAdapter(this, titlelist2);
			break;
		case 2:
			mDetailAdapter = new VideoListAdapter(this, titlelist3);
			break;
		}
		list.setAdapter(mDetailAdapter);
	}

	@Override
	public void reLoadView() {
		
	}
}
