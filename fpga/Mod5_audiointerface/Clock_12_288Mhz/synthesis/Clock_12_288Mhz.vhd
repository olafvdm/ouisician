-- Clock_12_288Mhz.vhd

-- Generated using ACDS version 16.0 211

library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity Clock_12_288Mhz is
	port (
		audio_clk_clk      : out std_logic;        --    audio_clk.clk
		ref_clk_clk        : in  std_logic := '0'; --      ref_clk.clk
		ref_reset_reset    : in  std_logic := '0'; --    ref_reset.reset
		reset_source_reset : out std_logic         -- reset_source.reset
	);
end entity Clock_12_288Mhz;

architecture rtl of Clock_12_288Mhz is
	component Clock_12_288Mhz_audio_pll_0 is
		port (
			ref_clk_clk        : in  std_logic := 'X'; -- clk
			ref_reset_reset    : in  std_logic := 'X'; -- reset
			audio_clk_clk      : out std_logic;        -- clk
			reset_source_reset : out std_logic         -- reset
		);
	end component Clock_12_288Mhz_audio_pll_0;

begin

	audio_pll_0 : component Clock_12_288Mhz_audio_pll_0
		port map (
			ref_clk_clk        => ref_clk_clk,        --      ref_clk.clk
			ref_reset_reset    => ref_reset_reset,    --    ref_reset.reset
			audio_clk_clk      => audio_clk_clk,      --    audio_clk.clk
			reset_source_reset => reset_source_reset  -- reset_source.reset
		);

end architecture rtl; -- of Clock_12_288Mhz
