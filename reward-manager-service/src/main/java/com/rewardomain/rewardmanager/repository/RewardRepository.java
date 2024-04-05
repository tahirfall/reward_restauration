package com.rewardomain.rewardmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rewardomain.rewardmanager.bean.Reward;

public interface RewardRepository extends JpaRepository<Reward, Long> {
	 Reward findById(long id);

	Reward findByConfirmationNumber(long reward_confirmation_number);

	Reward getByConfirmationNumber(long rewardNumber);
}
