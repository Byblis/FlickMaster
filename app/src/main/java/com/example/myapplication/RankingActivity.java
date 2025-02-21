public class RankingActivity extends AppCompatActivity {

    private RecyclerView rankingRecyclerView; // ✅ 追加
    private List<RankingEntry> rankingList;
    private Button replayButton, homeButton; // 🔥 ボタン追加

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        rankingRecyclerView = findViewById(R.id.rankingRecyclerView); // ✅ 修正
        replayButton = findViewById(R.id.replayButton); // ✅ ボタン取得
        homeButton = findViewById(R.id.homeButton); // ✅ ボタン取得

        rankingRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ランキングデータを取得してソート
        rankingList = RankingManager.getRanking(this);
        Collections.sort(rankingList, (a, b) -> b.getScore() - a.getScore());

        // 順位をつける
        int currentRank = 1;
        for (int i = 0; i < rankingList.size(); i++) {
            if (i > 0 && rankingList.get(i).getScore() == rankingList.get(i - 1).getScore()) {
                rankingList.get(i).setRank(rankingList.get(i - 1).getRank());
            } else {
                rankingList.get(i).setRank(currentRank);
            }
            currentRank++;
        }
    }
}



