package net.bozahouse.backend.services.statistic;



import net.bozahouse.backend.model.entities.stats.AppUserStatistic;
import net.bozahouse.backend.model.entities.stats.OfferStatistic;
import net.bozahouse.backend.model.entities.stats.SubscriptionStatistic;
import net.bozahouse.backend.model.entities.stats.TalentStatistic;

import java.util.List;

public interface StatisticService {
    void subscriptionStatistics() ;
    List<SubscriptionStatistic> listSubscriptionStatistic(int page, int size);

    void talentStatistics();

    List<TalentStatistic> listTalentStatistic(int page, int size);

    void offerStatistics();

    List<OfferStatistic> listOfferStatistic(int page, int size);

    void appUserStatistics() ;

    List<AppUserStatistic> listAppUserStatistic(int page, int size);
}
